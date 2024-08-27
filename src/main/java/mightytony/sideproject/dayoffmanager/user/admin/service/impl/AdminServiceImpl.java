package mightytony.sideproject.dayoffmanager.user.admin.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.auth.mapper.MemberMapper;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.Department;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.repository.DepartmentRepository;
import mightytony.sideproject.dayoffmanager.company.service.impl.CompanyServiceImpl;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.config.redis.RedisUtil;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request.AdminMemberUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.user.admin.repository.AdminRepository;
import mightytony.sideproject.dayoffmanager.user.admin.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AuthServiceImpl authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository memberRepository;
    private final MemberMapper memberMapper;
    private final CompanyRepository companyRepository;
    private final AdminRepository adminRepository;
    private final DepartmentRepository departmentRepository;
    private final RedisUtil redisUtil;
    //private final CompanyServiceImpl companyService;

    /**
     * 멤버 정보 조회 ( 유저 아이디 )
     */
    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto getEmployeeByUserId(String userId) {
        // 1. Member 조회
        Member findMember = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
        if (findMember.getStatus() != MemberStatus.APPROVED){
            throw new CustomException(ResponseCode.NOT_APPROVED);
        }
        // 2. 전달
        return memberMapper.toDTO(findMember);
    }

    /**
     * 조회 요청 한 유저의 회사를 조회 하는 기능
     */
    @Override
    @Transactional(readOnly = true)
    public Company checkYourCompany (HttpServletRequest request) {
        // 1. 해당 요청한 유저 조회
        String adminUserId = adminNameFromToken(request);

//        // 2. 해당 유저 조회 신청한 유저 회사 정보
//        Member findAdmin = memberRepository.findByUserId(adminUserId)
//                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
//
//        Company company = findAdmin.getCompany();
//        // 결과
//        return company;

        // 2. 캐시된 유저 정보를 통해 회사 정보 조회
        MemberLoginResponseDto userInformation = jwtTokenProvider.getCachedUserInformation(adminUserId);
        if(userInformation == null || userInformation.getCompanyId() == null){
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // 3. 회사 정보 조회\
        Company company = companyRepository.findById(userInformation.getCompanyId())
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        //Company company = companyService.getCompanyById(userInformation.getCompanyId());

        return company;
    }


    /**
     *  심사 대기중인 멤버 조회 페이징
     */
    @Override
    @Transactional(readOnly = true)
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

    /**
     * 해당 멤버 사원으로 등록 하기 (유저 아아디), 사번 부여
     */
    @Override
    @Transactional(readOnly = false)
    public void registerEmployee(AdminInviteNewMemberRequestDto dto, HttpServletRequest request) {
        // 1. 어드민 회사 조회
        Company myCompany = checkYourCompany(request);

        // 2. 어드민 회사에 해당 userId를 가졌고 심사대기중(PENDING)인 사람 조회
        Member newMember = adminRepository.findMemberByUserIdAndCompanyId(dto.getUserId(), myCompany.getId());

        // 3. 유저 승인 해주기

        // 3-1. 사번 중복 체크
        if(isEmployeeNumberDuplicate(dto.getEmployeeNumber(), myCompany.getId())) {
            throw new CustomException(ResponseCode.DUPLICATED_NUMBER);
        }
        // 3-2. 팀 명 체크
        Department department = departmentRepository.findDepartmentByCompany_IdAndName(myCompany.getId(), dto.getDepartmentName());

        if(!departmentRepository.existsDepartmentByCompany_IdAndName(myCompany.getId(), department.getName())){
            throw new CustomException(ResponseCode.NOT_FOUND_DEPARTMENT);
        }

        newMember.welcome(dto.getEmployeeNumber(), department);
    }

    /**
     * 등록 된 멤버 조회 페이징
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberResponseDto> getMembers(HttpServletRequest request, int page, int size) {
        Company myCompany = checkYourCompany(request);
        Long myCompanyId = myCompany.getId();

        // 기준
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        // 페이징 객체
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Member> members = memberRepository.findByCompanyIdAndStatus(myCompanyId, MemberStatus.APPROVED, pageable);

        return members.map(memberMapper::toDTO);
    }

    @Override
    public void registerDepartment(String departmentName, HttpServletRequest request) {
        // 1. 어드민 회사 조회
        Company myCompany = checkYourCompany(request);

        // 2. 어드민 회사에 요청과 중복 되는 부서 있는 지 체크
        if(departmentRepository.existsDepartmentByCompany_IdAndName(myCompany.getId(), departmentName)){
            throw new CustomException(ResponseCode.ALREADY_EXIST_DEPARTMENT);
        }

        // save
        Department department = Department
                .builder()
                .company(myCompany)
                .name(departmentName)
                .build();

        departmentRepository.save(department);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateMemberInfo(HttpServletRequest request, Long companyId, String userId, AdminMemberUpdateRequestDto requestDto) {

        Member member = memberRepository.findByUserIdAndCompanyId(userId, companyId)
                .orElseThrow(()-> new CustomException(ResponseCode.NOT_FOUND_USER));

        Department department = departmentRepository.findDepartmentByCompany_IdAndName(companyId, requestDto.getDepartmentName());
        //requestDto.getDepartmentName()

        member.updateFromAdmin(requestDto, department);
        memberRepository.save(member);

        redisUtil.deleteUserFromCache(userId);
        MemberLoginResponseDto loginDTO = memberMapper.toLoginDTO(member);
        redisUtil.saveUser(userId, loginDTO);
    }

    private boolean isEmployeeNumberDuplicate(String employeeNumber, Long companyId) {
        return adminRepository.existsByEmployeeNumberAndCompanyId(employeeNumber, companyId);
    }


    private String adminNameFromToken(HttpServletRequest request) {
        // 1. 해당 요청한 유저 조회
        String accessToken = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String adminName = authentication.getName();

        return adminName;
    }

}
