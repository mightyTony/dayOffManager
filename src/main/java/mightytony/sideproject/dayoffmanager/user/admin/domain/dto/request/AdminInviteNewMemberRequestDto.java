package mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request;

import lombok.Data;

@Data
public class AdminInviteNewMemberRequestDto {

    private String userId;
    private String employeeNumber;
    private String departmentName;
}
