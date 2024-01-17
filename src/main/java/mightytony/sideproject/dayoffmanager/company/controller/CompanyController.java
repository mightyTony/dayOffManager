package mightytony.sideproject.dayoffmanager.company.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CreateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CreateCompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.mapper.CompanyMapper;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Tag(name = "회사(업체)", description = "회사 관련 api 입니다")
public class CompanyController {

    // 의존성 주입
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    /**
     * 회원 가입
     */
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid CreateCompanyRequestDto req) {

        // 1. 이미 등록 된 회사인지 체크
        boolean isDuplicate;
        isDuplicate = companyService.isDuplicate(req.getBusinessNumber());
        // 2. 중복 아닐 시 등록
        if(isDuplicate) {
           ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Company company = companyService.save(req);
        }
        // 3. 결과
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @apiNote 모든 업체 조회
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<Company>> getAllCompany() {

        List<Company> allCompany = companyService.findAll();

        return ResponseEntity.ok(allCompany);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id) {

        CompanyResponseDto companyResponseDto = companyService.findById(id);

        return ResponseEntity.ok(companyResponseDto);
    }
}
