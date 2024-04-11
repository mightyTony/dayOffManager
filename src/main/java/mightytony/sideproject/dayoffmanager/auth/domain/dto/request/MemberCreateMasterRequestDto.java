package mightytony.sideproject.dayoffmanager.auth.domain.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // 밑의 변수를 JSON으로 받을 때 SnakeCase로 받음
public class MemberCreateMasterRequestDto {
    @Schema(description = "유저 아이디", example = "master")
    private String userId;
    @Schema(description = "비밀번호", example = "password123")
    private String password;
    @Schema(description = "이름", example = "마스터")
    private String name;
    @Schema(description = "이메일", example = "mightytonykr@gmail.com")
    private String email;
    @Schema(description = "전화번호", example = "01053612197")
    private String phoneNumber;
    @Schema(description = "프로필 이미지", example = "testImage.jpg")
    private String profileImage;
    //private String employeeNumber; // 사번

}
