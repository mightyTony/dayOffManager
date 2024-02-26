package mightytony.sideproject.dayoffmanager.admin.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminInviteNewMemberRequestDto {
    @Schema(description = "유저 ID", example = "testUser")
    private String userId;

    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;

    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;
}
