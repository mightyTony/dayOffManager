package mightytony.sideproject.dayoffmanager.member.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter@Setter
public class MemberLoginRequestDto {

    @JsonProperty("user_id")
    @Schema(description = "유저 아이디", example = "testUser")
    private String userId;
    @Schema(description = "유저 비밀번호", example = "password123")
    private String password;
}
