package mightytony.sideproject.dayoffmanager.master.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import mightytony.sideproject.dayoffmanager.master.domain.dto.request.MasterInviteAdminRequestDto;
import mightytony.sideproject.dayoffmanager.master.repository.MasterRepository;
import mightytony.sideproject.dayoffmanager.master.service.MasterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@RequiredArgsConstructor
@Service
@Slf4j
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final CompanyRepository companyRepository;
    private final AuthRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false)
    public String inviteAdminToCompany(MasterInviteAdminRequestDto dto) {
        // 1. 해당 유저 유무
        if(memberRepository.existsMemberByUserId(dto.getUserId())){
            throw new CustomException(ResponseCode.User_Already_Existed);
        }

        // 2. 해당 회사 유무
        Company targetCompany = companyRepository.findByBrandName(dto.getBrandName());
        if(targetCompany == null) {
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // 3. 해당 유저 회사에 관리자로 등록
        Member admin = Member.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .roles(Collections.singletonList(MemberRole.ADMIN))
                .company(targetCompany)
                .status(MemberStatus.APPROVED)
                .build();

        //admin.updateToAdmin();

        // 4. 등록
        masterRepository.save(admin);

        // 5. 로그
        log.info("JOIN: {}({}) 님이 어드민 등록 하였습니다.", admin.getUserId(), admin.getName());
        return "등록 완료";
    }

//    @Transactional(readOnly = false)
//    @Override
//    public String deleteCompany(String brandName) {
//        Company company = companyRepository.findByBrandName(brandName);
//
//        if(company == null) {
//            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
//        }
//
//        companyRepository.deleteCompany(brandName);
//        return "기업 삭제 : " + brandName + " 완료";
//    }
}
