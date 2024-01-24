package mightytony.sideproject.dayoffmanager.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyCreateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
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

    /**
     * 회원 가입
     */
    @Operation(summary = "기업 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created")
    })
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid CompanyCreateRequestDto req) {
        companyService.save(req);
        // 3. 결과
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @apiNote 모든 업체 조회
     *
     */
    @Operation(summary = "모든 등록 된 기업 조회")
    @GetMapping("/")
    public ResponseEntity<List<Company>> getAllCompany() {

        List<Company> allCompany = companyService.findAll();

        return ResponseEntity.ok(allCompany);
    }

    @Operation(summary = "Id로 기업 조회")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id) {

        CompanyResponseDto companyResponseDto = companyService.findById(id);

        return ResponseEntity.ok(companyResponseDto);
    }

    /*
     * 업체 수정 api
     */
    // FIXME 고쳐야해
    @Operation(summary = "특정 기업 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@RequestBody @Valid CompanyUpdateRequestDto req) {
        companyService.updateCompany(req);
        return ResponseEntity.ok().build();
    }

    /*
      업체 삭제 api
     */
    @Operation(summary = "기업 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);

        return ResponseEntity.ok().build();
    }
}
