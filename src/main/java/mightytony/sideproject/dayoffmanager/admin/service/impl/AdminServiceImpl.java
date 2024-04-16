package mightytony.sideproject.dayoffmanager.admin.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.admin.service.AdminService;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.auth.mapper.MemberMapper;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AuthServiceImpl authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository memberRepository;
    private final MemberMapper memberMapper;
    private final CompanyRepository companyRepository;

    public ResponseEntity<BasicResponse<Void>> inviteNewEmployee(AdminInviteNewMemberRequestDto requestDto, HttpServletRequest request) {

        // 1. Admin 체크
        String accessTokenFromRequest = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessTokenFromRequest);

        // 2. 해당 어드민이 소속된 회사 값 가져오기

        // 3. 새로 온 사람 회사에 소속 시키기(Member -> Employee)
        return ResponseUtil.ok();
    }

    @Override
    public MemberResponseDto getEmployeeByUserId(String userId) {
        // 1. Member 조회
        Member findMember = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
        // 2. 전달
        return memberMapper.toDTO(findMember);
    }

    /**
     * 조회 요청 한 유저의 회사를 조회 하는 기능
     */
    @Override
    public Company checkYourCompany (HttpServletRequest request) {
        // 1. 해당 요청한 유저 조회
        String accessToken = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String AdminName = authentication.getName();

        // 2. 해당 유저 조회 신청한 유저 회사 정보
        Member findAdmin = memberRepository.findByUserId(AdminName)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));

        Company company = findAdmin.getCompany();
        // 결과

        return company;
    }

    /**
     *  심사 대기중인 멤버 조회 페이징
     */
    @Override
    public Page<MemberResponseDto> checkJoinMemberAndChangeStatus(HttpServletRequest request, int page, int size) {

        Company myCompany = checkYourCompany(request);
        Long myCompanyId = myCompany.getId();

        // 기준
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        // 페이징 객체
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Member> members = memberRepository.findByCompanyIdAndStatus(myCompanyId, MemberStatus.PENDING, pageable);

        return members.map(memberMapper::toDTO);
        //return memberRepository.findByCompanyIdAndStatus(myCompanyId, MemberStatus.PENDING, pageable);
    }
}
