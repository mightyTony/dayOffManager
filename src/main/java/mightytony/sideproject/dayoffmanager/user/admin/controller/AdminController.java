package mightytony.sideproject.dayoffmanager.user.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request.AdminMemberUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.user.admin.service.AdminService;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN 권한이 있어야 해당 메서드 접근 가능
@Tag(name = "어드민(업체)", description = "어드민 관련 api / 로그인 필요")
public class AdminController {

    private final AdminService adminService;
    @Operation(summary = "등록 신청 한 멤버 페이징 조회(Id)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("/members/pending")
    public ResponseEntity<BasicResponse<Page<MemberResponseDto>>> checkNewMember(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Page<MemberResponseDto> members = adminService.checkJoinMemberAndChangeStatus(request, page, size);

        //body, code, msg
        return ResponseUtil.ok(members, HttpStatus.OK.value(), "성공");
    }

    @Operation(summary = "등록 된 멤버 페이징 조회(Id)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("/members")
    public ResponseEntity<BasicResponse<Page<MemberResponseDto>>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Page<MemberResponseDto> members = adminService.getMembers(request, page, size);


        return ResponseUtil.ok(members, HttpStatus.OK.value(), "성공");
    }

    @Operation(summary = "멤버 조회(Id)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("")
    public ResponseEntity<BasicResponse<MemberResponseDto>> getEmployee(@RequestParam String userId) {
        MemberResponseDto dto = adminService.getEmployeeByUserId(userId);

        return ResponseUtil.ok(dto);
    }

    @Operation(summary = "멤버 회사 등록 승인, 사번 부여, 부서 배치")
    @PutMapping("/register")
    public ResponseEntity<BasicResponse<Void>> registerEmployee(@RequestBody AdminInviteNewMemberRequestDto dto, HttpServletRequest request) {
        log.info("new member dto : {}", dto);
        adminService.registerEmployee(dto, request);

        return ResponseUtil.ok();
    }

    @Operation(summary = "멤버 회사 등록 거절")
    @DeleteMapping("/reject/{userId}")
    public ResponseEntity<BasicResponse<Void>> rejectJoinMember(HttpServletRequest request,
                                                                @PathVariable String userId) {
        adminService.rejectJoinMember(request, userId);
        log.info("멤버 회사 등록 거절 완료");
        return ResponseUtil.ok();
    }

    @Operation(summary = "해당 멤버 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @PatchMapping("/{companyId}/{userId}")
    public ResponseEntity<BasicResponse<MemberResponseDto>> adminUpdateMemberInfo(HttpServletRequest request,
                                                                     @PathVariable Long companyId,
                                                                     @PathVariable String userId,
                                                                     @RequestBody AdminMemberUpdateRequestDto requestDto) {

        MemberResponseDto memberResponseDto = adminService.updateMemberInfo(request, companyId, userId, requestDto);

        return ResponseUtil.ok(memberResponseDto);
    }

    @Operation(summary = "해당 멤버 정보 삭제(soft)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @DeleteMapping("/{companyId}/{userId}")
    public ResponseEntity<BasicResponse<Void>> deleteUserFromAdmin(HttpServletRequest request,
                                                                   @PathVariable Long companyId,
                                                                   @PathVariable String userId) {

        adminService.deleteUserFromAdmin(request, companyId, userId);

        return ResponseUtil.ok();
    }

    @Operation(summary = "부서 등록")
    @PostMapping("/register/department")
    public ResponseEntity<BasicResponse<Void>> registerDepartment(@RequestParam String departmentName, HttpServletRequest request) {
        adminService.registerDepartment(departmentName, request);

        return ResponseUtil.ok();
    }

    /**
     * 휴가 신청 상세 조회
     */





    /**
     * 휴가 신청 승인/반려
     */

    /**
     * 모든 휴가 히스토리 조회
     */

}
