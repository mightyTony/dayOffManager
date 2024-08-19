package mightytony.sideproject.dayoffmanager.dayoff.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.service.impl.CompanyServiceImpl;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffType;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import mightytony.sideproject.dayoffmanager.dayoff.mapper.DayOffMapper;
import mightytony.sideproject.dayoffmanager.dayoff.repository.DayOffRepository;
import mightytony.sideproject.dayoffmanager.dayoff.service.DayOffService;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import mightytony.sideproject.dayoffmanager.user.admin.service.impl.AdminServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayOffServiceImpl implements DayOffService {

    private final AuthRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final DayOffRepository dayOffRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthServiceImpl authService;
    private final AdminServiceImpl adminService;
    private final DayOffMapper dayOffMapper;

    @Override
    @Transactional(readOnly = false)
    public void requestDayOff(DayOffApplyRequestDto requestDto, HttpServletRequest request ) {

        String authToken = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(authToken);
        String userId = authentication.getName();

        // 1. 해당 유저 조회
        Member user = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
        //MemberLoginResponseDto userInformation = jwtTokenProvider.getCachedUserInformation(userId);

        // 2. 회사에 등록 된 유저인지 조회
        Company company = user.getCompany();
        MemberStatus userStatus = user.getStatus();

        if(companyRepository.findById(company.getId()).isEmpty()) {
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }
        if(!userStatus.equals(MemberStatus.APPROVED)){
            throw new CustomException(ResponseCode.NOT_APPROVED);
        }
        // 3. 유저 휴가 개수 체크 (0.5개 이상이여야함)
        if(user.getDayOffCount() < 0.5) {
            throw new CustomException(ResponseCode.NOT_ENOUGH_DAYOFF);
        }

        // 날짜 유효성 검증
        validateDayOffRequest(requestDto.getStartDate(), requestDto.getEndDate(), requestDto.getDuration(), requestDto.getDayOffType());

        // 4. 휴가 신청
        DayOff dayOff = DayOff.builder()
                .member(user)
                .type(requestDto.getDayOffType())
                .duration(requestDto.getDuration())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .status(DayOffStatus.PENDING)
                .build();

        // TODO 신청이 허가 되면 user 휴가 카운트 - 해줘야함.
        /**
         * // 감소되어야 할 휴가 일수 계산
         *     double remainingDayOff = user.getDayOffCount() - requestDto.getDuration();
         *     user.updateDayOffCount(remainingDayOff);
         */
        dayOffRepository.save(dayOff);
    }

    // 휴가 일수 계산 및 검증
    private void validateDayOffRequest(LocalDate startDate, LocalDate endDate, double requestDays, DayOffType dayOffType) {
        long between = ChronoUnit.DAYS.between(startDate,endDate.plusDays(1));

//        if (between != requestDays) {
//            throw new CustomException(ResponseCode.INVALID_DAYOFF_REQUEST);
//        }
        // 반차 반반차 계산도 고려

        // 휴가 종류 당 로직
         switch (dayOffType) {
             case NORMAL :
                 if (between != requestDays) {
                     throw new CustomException(ResponseCode.INVALID_DAYOFF_REQUEST);
                 }
                 break;
             case AM_HALF:
             case PM_HALF:
                 if (between != 1 || requestDays != 0.5) {
                     throw new CustomException(ResponseCode.INVALID_DAYOFF_REQUEST);
                 }
                 break;
             case AM_QUARTER:
             case PM_QUARTER:
                 if (between != 1 || requestDays != 0.25) {
                     throw new CustomException(ResponseCode.INVALID_DAYOFF_REQUEST);
                 }
                 break;
             // FIXME 나머지 휴가 종류 일단 보류...
             default:
                 throw new CustomException(ResponseCode.INVALID_DAYOFF_REQUEST);
         }

    }

    @Override
    public Page<DayOffApplyResponseDto> getAllDayOff(Long companyId, HttpServletRequest request, int page, int size) {

        Company myCompany = adminService.checkYourCompany(request);
        Long myCompanyId = companyId;

        if(!myCompany.getId().equals(companyId)){
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // Sort
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        // Paging
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DayOff> dayOffs = dayOffRepository.findByMember_Company_IdAndStatus(companyId, DayOffStatus.PENDING, pageable);

        return dayOffs.map(dayOffMapper::toDTO);
    }
}
