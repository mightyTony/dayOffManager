package mightytony.sideproject.dayoffmanager.admin.domain.dto.request;

import lombok.Data;

@Data
public class AdminInviteNewMemberRequestDto {

    private String userId;

    private String email;

    private String phoneNumber;
}
