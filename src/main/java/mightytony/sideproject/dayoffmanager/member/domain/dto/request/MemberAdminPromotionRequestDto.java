package mightytony.sideproject.dayoffmanager.member.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberAdminPromotionRequestDto {
    @Schema(description = "유저 아이디", example = "testUser")
    private String userId;
    @Schema(description = "이름", example = "테스트")
    private String name;
    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;
}
