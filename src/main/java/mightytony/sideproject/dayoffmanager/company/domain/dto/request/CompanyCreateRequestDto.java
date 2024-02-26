package mightytony.sideproject.dayoffmanager.company.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 회사 등록 요청을 위한 DTO
 */
@Data
@Schema(description = "업체 등록 dto")
//@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyCreateRequestDto {

    @NotNull
    @Size(min = 10, max = 10, message = "사업자등록번호는 10자리여야 합니다.")
    @JsonProperty("b_no")
    @Schema(description = "사업자등록번호", example = "6518100883")
    private String businessNumber;

    @Pattern(regexp = "^[0-9]{8}$", message = "개업일자는 YYYYMMDD 포맷이어야 합니다.")
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

    /*
     * @Pattern : 해당 정규식 제약조건 걸기
     * @Size : 최소 최대 사이즈 제약조건
     * @NotBlank : 공백, null 제약 조건
     * @JsonProperty : Json에서 받은 이름과 Dto 안의 변수 명이 다를 때 매칭을 시켜준다.
     * @Schema(description = "") : Swagger 에서 해당 부분 설명 등록
     */
}
