package mightytony.sideproject.dayoffmanager.auth.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // 밑의 변수를 JSON으로 받을 때 SnakeCase로 받음
public class MemberCreateRequestDto {
    @Schema(description = "유저 아이디", example = "testUser")
    @Size(min = 4, message = "유저 아이디는 4글자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "유저 아이디는 영어와 숫자를 혼합하거나 영어로만 이루어져야 합니다.")
    @NotNull
    private String userId;

    @Schema(description = "비밀번호", example = "password123")
    @Size(min = 4, max = 30, message = "비밀번호는 4글자 이상 30글자 이하이어야 합니다.")
    @NotNull
    private String password;

    @Schema(description = "이름", example = "테스트")
    @NotNull
    private String name;

    @Schema(description = "이메일", example = "test@gmail.com")
    @NotNull @Email
    private String email;

    @Schema(description = "전화번호", example = "01012345678")
    @NotNull
    @Pattern(regexp = "^010[0-9]{8}$", message = "010으로 시작해서 총 11자 이어야 합니다.")
    private String phoneNumber;

//    @NotBlank(message = "상호가 비어있습니다.")
//    @JsonProperty("b_nm")
//    @Schema(description = "상호 명", example = "주식회사 티윈")
//    private String brandName;
    @NotNull
    @Size(min = 10, max = 10, message = "사업자등록번호는 10자리여야 합니다.")
    @JsonProperty("b_no")
    @Schema(description = "사업자등록번호", example = "6081469076")
    private String businessNumber;

    @Schema(description = "입사 날짜", example = "2024-04-23")
    private LocalDate hireDate;

}
