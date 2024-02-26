package mightytony.sideproject.dayoffmanager.company.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "업체 조회 dto")
@Data
public class CompanyRequestDto {

    @JsonIgnore
    private Long id;

    @Schema(description = "사업자 등록번호", example = "6518100883")
    @NotNull
    private String businessNumber;

    @Schema(description = "대표자 성명", example = "조영현")
    @NotNull
    private String primaryRepresentName1;

    @Schema(description = "상호 명", example = "주식회사 티윈")
    @NotNull
    private String brandName;

}
