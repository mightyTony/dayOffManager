package mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;

@Data
public class AdminInviteNewMemberRequestDto {
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

    private String userId;
    private String employeeNumber;
    @Schema(description = "부서")
    private String departmentName;
    @Schema(description = "유저 등급", example = "ADMIN")
    private MemberRole role;
}
