package mightytony.sideproject.dayoffmanager.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<BasicResponse<Void>> inviteNewEmployee(AdminInviteNewMemberRequestDto requestDto, HttpServletRequest request);
}
