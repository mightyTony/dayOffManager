package mightytony.sideproject.dayoffmanager.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.admin.service.AdminService;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
// FIXME > 개발 끝나면 바꿔야함
//@PreAuthorize("hasAuthority('ADMIN')") // ADMIN 권한이 있어야 해당 메서드 접근 가능
@Tag(name = "어드민(업체)", description = "어드민 관련 api / 로그인 필요")
public class AdminController {

    private final AdminService adminService;

    /**
     * 등록 요청한 유저 조회 하기
     */
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

    /**
     * 멤버 정보 조회 ( 유저 아이디 )
     */
    @Operation(summary = "멤버 조회(Id)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("/")
    public ResponseEntity<BasicResponse<MemberResponseDto>> getEmployee(@RequestParam String userId) {
        MemberResponseDto dto = adminService.getEmployeeByUserId(userId);

        return ResponseUtil.ok(dto);
    }


    /**
     * 해당 멤버 사원으로 등록 하기 (유저 아아디), 사번 부여
     */
    @Operation(summary = "멤버 회사 등록 승인")
    @PutMapping("/register/{userId}")
    public ResponseEntity<BasicResponse<Void>> registerEmployee(@RequestBody AdminInviteNewMemberRequestDto dto, HttpServletRequest request) {
        adminService.registerEmployee(dto, request);

        return ResponseUtil.ok();
    }

    /**
     * 해당 멤버 정보 수정
     */

    /**
     * 해당 정보 삭제 (soft 삭제)
     */

    /**
     * 모든 휴가 신청 조회
     */

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
