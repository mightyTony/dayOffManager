package mightytony.sideproject.dayoffmanager.master.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.Employee;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import mightytony.sideproject.dayoffmanager.master.domain.dto.request.MasterInviteAdminRequestDto;
import mightytony.sideproject.dayoffmanager.master.repository.MasterRepository;
import mightytony.sideproject.dayoffmanager.master.service.MasterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final CompanyRepository companyRepository;
    private final AuthRepository memberRepository;

    @Override
    @Transactional(readOnly = false)
    public String inviteAdminToCompany(MasterInviteAdminRequestDto dto) {
        // 1. 해당 유저 유무
        Member targetUser = memberRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
        // 2. 해당 회사 유무
        Company targetCompany = companyRepository.findByBrandName(dto.getBrandName());
        if(targetCompany == null) {
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // 3. 이메일 중복 검사
        if(masterRepository.existsByEmail(targetUser.getEmail())){
           throw new CustomException(ResponseCode.EMAIL_EXISTED);
        }

        // 3. 해당 유저 회사에 관리자로 등록
        Employee employee = Employee.builder()
                .company(targetCompany)
                .email(targetUser.getEmail())
                .userId(targetUser.getUserId())
                .name(targetUser.getName())
                .password(targetUser.getPassword())
                .phoneNumber(targetUser.getPhoneNumber())
                .profileImage(targetUser.getProfileImage())
                .roles(new ArrayList<>(targetUser.getRoles()))
                .build();
//        Employee employee = Employee.builder()
//                .userId(targetUser.getUserId())
//                .password(targetUser.getPassword())
//                .name(targetUser.getName())
//                .email(targetUser.getEmail())
//                .phoneNumber(targetUser.getPhoneNumber())
//                .profileImage(targetUser.getProfileImage())
//                .roles(new ArrayList<>(targetUser.getRoles()))
//                .company(targetCompany)
//                //.employeeNumber()
//                //.hireDate(LocalDate.now())
//                //.vacationCount(0.0)
//                .build();
        log.info("########################targetUser.toString() : {}", targetUser.getEmail());
        log.info("########################employee.toString() : {}",employee.getEmail());
        log.info("########################company.toString(): {}" , targetCompany.getBrandName());
        // 4. 등록
        masterRepository.save(employee);

        return "등록 완료";
    }
}
