package mightytony.sideproject.dayoffmanager.company.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CreateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    // 의존성 주입
    private final CompanyService companyService;

    /**
     * 회원 가입
     */
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody CreateCompanyRequestDto req) {

        // 1. 이미 등록 된 회사인지 체크
        boolean isDuplicate;
        isDuplicate = companyService.isDuplicate(req.getBusinessNumber());
        // 2. 등록
        if(isDuplicate == false) {

        }
        // 3. 결과

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
