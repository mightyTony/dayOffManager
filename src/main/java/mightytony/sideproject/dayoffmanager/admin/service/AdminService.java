package mightytony.sideproject.dayoffmanager.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<BasicResponse<Void>> inviteNewEmployee(AdminInviteNewMemberRequestDto requestDto, HttpServletRequest request);

    MemberResponseDto getEmployeeByUserId(String userId);

    //조회 요청 한 유저의 회사를 조회 하는 기능
    Company checkYourCompany (HttpServletRequest request);

    Page<MemberResponseDto> checkJoinMemberAndChangeStatus(HttpServletRequest request, int page, int size);
}
