package mightytony.sideproject.dayoffmanager.dayoff.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;
import mightytony.sideproject.dayoffmanager.dayoff.repository.DayOffRepository;
import mightytony.sideproject.dayoffmanager.dayoff.service.DayOffService;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayOffServiceImpl implements DayOffService {

    private final AuthRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final DayOffRepository dayOffRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthServiceImpl authService;

    @Override
    @Transactional(readOnly = false)
    public void requestDayOff(DayOffApplyRequestDto requestDto, HttpServletRequest request ) {

        String authToken = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(authToken);
        String userId = authentication.getName();

        // 1. 해당 유저 조회
        //String userId = requestDto.getUser_id();
        Member user = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));

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

        // 4. 휴가 신청
        DayOff dayOff = DayOff.builder()
                .member(user)
                .type(requestDto.getDayOffType())
                .duration(requestDto.getDuration())
                .status(DayOffStatus.PENDING)
                .build();

        // TODO 신청이 허가 되면 user 휴가 카운트 - 해줘야함.
        dayOffRepository.save(dayOff);
    }
}
