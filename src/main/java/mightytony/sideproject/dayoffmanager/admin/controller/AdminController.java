package mightytony.sideproject.dayoffmanager.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.admin.service.AdminService;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN 권한이 있어야 해당 메서드 접근 가능
public class AdminController {

    private final AdminService adminService;

    /**
     * 소속된 기업에 유저 등록 하기
     */
    public ResponseEntity<BasicResponse<Void>> inviteNewMember(@RequestBody AdminInviteNewMemberRequestDto dto, HttpServletRequest request) {

        adminService.inviteNewEmployee(dto, request);

        return ResponseUtil.ok();
    }

    /**
     * 멤버 정보 조회(기업 내 모든 유저 조회)
     */
//    @GetMapping()
//    public ResponseEntity<AdminResponseDto> getMember()

}
