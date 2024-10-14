package mightytony.sideproject.dayoffmanager.user.teamleader.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.auth.mapper.MemberMapper;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import mightytony.sideproject.dayoffmanager.dayoff.mapper.DayOffMapper;
import mightytony.sideproject.dayoffmanager.dayoff.repository.DayOffRepository;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import mightytony.sideproject.dayoffmanager.user.admin.service.AdminService;
import mightytony.sideproject.dayoffmanager.user.teamleader.repository.TeamLeaderRepostory;
import mightytony.sideproject.dayoffmanager.user.teamleader.service.TeamLeaderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamLeaderServiceImpl implements TeamLeaderService {

    private final TeamLeaderRepostory teamLeaderRepostory;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final DayOffRepository dayOffRepository;
    private final AuthServiceImpl authService;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final DayOffMapper dayOffMapper;
    private final MemberMapper memberMapper;
    @Override
    @Transactional
    public void processDayOffRequest(Long companyId, Long departmentId, String userId, Long dayOffId, DayOffStatus status, HttpServletRequest request) {
        // 1. 사원과 팀장 회사, 같은 부서 같은 지 체크
        if(!isSameDepartment(request, departmentId)) {
            throw new CustomException(ResponseCode.NO_PERMISSION);
        }

        // 2. 승인 or 반려
        DayOff dayOff = dayOffRepository.findById(dayOffId)
                .orElseThrow(()-> new CustomException(ResponseCode.NOT_FOUND_DAYOFF));

        // 3. 업데이트
        dayOff.ApproveOrReject(status);

        dayOffRepository.save(dayOff);
    }

    @Override
    public Page<DayOffApplyResponseDto> getMyTeamDayOffs(Long companyId, Long departmentId, HttpServletRequest request, int page, int size) {

        Company myCompany = adminService.checkYourCompany(request);
        Long myCompanyId = companyId;

        if(!myCompany.getId().equals(companyId)) {
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // Sort
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        // Paging
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<DayOff> dayOffs = dayOffRepository.findByMember_Company_Id_AndStatusAndMember_Department_Id(companyId, DayOffStatus.PENDING, departmentId, pageable);

        return dayOffs.map(dayOffMapper::toDTO);
    }

    @Override
    public Page<MemberResponseDto> getMembers(Long companyId, Long departmentId, HttpServletRequest request, int page, int size) {
        Company myCompany = adminService.checkYourCompany(request);

        if(!myCompany.getId().equals(companyId)) {
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // Sort
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        // Paging
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Member> members = authRepository.findByCompany_IdAndDepartment_IdAndStatus(companyId, departmentId, MemberStatus.APPROVED, pageable);

        return members.map(memberMapper::toDTO);
    }

    private boolean isSameDepartment(HttpServletRequest request, Long departmentId) {
        // 팀장 아이디 찾기
        String authToken = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(authToken);
        String TL_userId = authentication.getName();

        Member teamLeader = authRepository.findByUserId(TL_userId).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));

        return teamLeader.getDepartment().getId().equals(departmentId);
    }
}
