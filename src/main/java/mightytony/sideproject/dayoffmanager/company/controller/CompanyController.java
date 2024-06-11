package mightytony.sideproject.dayoffmanager.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyCreateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanySearchRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanySearchResponseDto;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@Tag(name = "회사(업체)", description = "회사 관련 api / 로그인 필요")
public class CompanyController {

    // 의존성 주입
    private final CompanyService companyService;

    /**
     * 기업 등록
     */
    @Operation(summary = "기업 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created")
    })
    @PostMapping("/join")
    public ResponseEntity<BasicResponse<Void>> join(@RequestBody @Valid CompanyCreateRequestDto req) {
        companyService.save(req);

        return ResponseUtil.ok();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @apiNote 모든 업체 조회
     *
     */
    @Operation(summary = "모든 등록 된 기업 조회")
    @GetMapping("/")
    public ResponseEntity<BasicResponse<List<Company>>> getAllCompany() {

        List<Company> allCompany = companyService.findAll();

        return ResponseUtil.ok(allCompany);
    }

    @Operation(summary = "상호명으로 기업 저회")
    @GetMapping("/brandName")
    public ResponseEntity<BasicResponse<CompanyResponseDto>> getCompanyByBrandName(@RequestParam String brandName) {

        CompanyResponseDto response = companyService.findByBrandName(brandName);
        System.out.println("CompanyController.getCompanyByBrandName = " + brandName);
        System.out.println("response = " + response);
        return ResponseUtil.ok(response);
    }

    /*
     * 업체 수정 api
     */
    @Operation(summary = "특정 기업 수정")
    @PutMapping("/brandName")
    @PreAuthorize("hasAnyRole('MASTER','ADMIN')")
    public ResponseEntity<BasicResponse<Void>> updateCompany(@RequestBody @Valid CompanyUpdateRequestDto req) {
        companyService.updateCompany(req);
        //return ResponseEntity.ok().build();
        return ResponseUtil.ok();
    }

    /*
      업체 삭제 api
     */
    @Operation(summary = "기업 삭제")
    @DeleteMapping("/brandName")
    @PreAuthorize("hasAnyRole('MASTER','ADMIN')")
    public ResponseEntity<BasicResponse<Void>> deleteCompany(@RequestParam String brandName) {
        companyService.deleteCompany(brandName);

        //return ResponseEntity.ok().build();
        return ResponseUtil.ok();
    }

    /**
     * 기업 사업자등록번호 조회
     */
    @Operation(summary = "공공데이터 api 사업자 조회")
    @PostMapping("/search-companies")
    public ResponseEntity<BasicResponse<CompanySearchResponseDto>> searchCompanies(@RequestBody CompanySearchRequestDto req) throws URISyntaxException {
        CompanySearchResponseDto dto = companyService.searchCompany(req);

        return ResponseUtil.ok(dto);
    }
}
