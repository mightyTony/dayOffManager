package mightytony.sideproject.dayoffmanager.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import org.springframework.data.domain.Page;

public interface AdminService {

    MemberResponseDto getEmployeeByUserId(String userId);

    //조회 요청 한 유저의 회사를 조회 하는 기능
    Company checkYourCompany (HttpServletRequest request);

    Page<MemberResponseDto> checkJoinMemberAndChangeStatus(HttpServletRequest request, int page, int size);


    void registerEmployee(AdminInviteNewMemberRequestDto dto, HttpServletRequest request);

    Page<MemberResponseDto> getMembers(HttpServletRequest request, int page, int size);
}
