package mightytony.sideproject.dayoffmanager.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberUpdateRequestDto {

//    @Schema(description = "비밀번호", example = "password123")
//    @Size(min = 4, max = 30, message = "비밀번호는 4글자 이상 30글자 이하이어야 합니다.")
//    @NotNull
//    private String password;

    @Schema(description = "이름", example = "테스트")
    @NotNull
    private String name;

    @Schema(description = "이메일", example = "test@gmail.com")
    @NotNull @Email(message = "유효 하지 않은 이메일 형식 입니다.")
    private String email;

    @Schema(description = "전화번호", example = "01012345678")
    @NotNull
    @Pattern(regexp = "^010[0-9]{8}$", message = "010으로 시작해서 총 11자 이어야 합니다.")
    private String phoneNumber;

    // TODO if null = 기본 이미지 저장
    //@Nullable
    private String profileImage;
}
