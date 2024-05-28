package mightytony.sideproject.dayoffmanager.company.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "업체 사업자등록번호 api 검증 dto")
public class CompanyValidationRequestDto {
    @NotNull
    @Size(min = 10, max = 10, message = "사업자등록번호는 10자리여야 합니다.")
    @JsonProperty("b_no")
    @Schema(description = "사업자등록번호", example = "6518100883")
    private String businessNumber;

    @Pattern(regexp = "^(200[0-9]|20[1-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$", message = "개업일자는 YYYYMMDD 포맷이어야 합니다.")
    @NotNull
    @JsonProperty("start_dt")
    @Schema(description = "개업일자", example = "20180202")
    private String startDate;

    @NotBlank(message = "대표자 성명이 비어있습니다.")
    @JsonProperty("p_nm")
    @Schema(description = "대표자 성명", example = "조영현")
    private String primaryRepresentName1;

    @NotBlank(message = "상호가 비어있습니다.")
    @JsonProperty("b_nm")
    @Schema(description = "상호 명", example = "주식회사 티윈")
    private String brandName;
}
