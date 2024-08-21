package mightytony.sideproject.dayoffmanager.user.master.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * 관리자 등록 요청을 위한 DTO
 */
@Data
@Schema(description = "관리자 등록 요청 dto")
public class MasterInviteAdminRequestDto {

    @Schema(description = "유저 아이디", example = "admin")
    @Size(min = 4, message = "유저 아이디는 4글자 이상이어야 합니다.")
    @NotNull
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "유저 아이디는 영어와 숫자를 혼합하거나 영어로만 이루어져야 합니다.")
    private String userId;

    @Schema(description = "비밀번호", example = "password123")
    @NotNull
    @Size(min = 4, max = 30, message = "비밀번호는 4글자 이상 30글자 이하이어야 합니다.")
    private String password;

    @Schema(description = "이름", example = "어드민")
    @NotNull
    private String name;

    @Schema(description = "이메일", example = "admin@gmail.com")
    @NotNull @Email
    private String email;

    @Schema(description = "전화번호", example = "01098745112")
    @NotNull
    @Pattern(regexp = "^010[0-9]{8}$", message = "010으로 시작해서 총 11자 이어야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "상호가 비어있습니다.")
    @JsonProperty("b_nm")
    @Schema(description = "상호 명", example = "주식회사 티윈")
    private String brandName;

    @Schema(description = "입사 날짜", example = "2024-04-23")
    private LocalDate hireDate;
}
