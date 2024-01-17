package mightytony.sideproject.dayoffmanager.company.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "업체 수정 dto")
@Data
public class UpdateCompanyRequestDto {

    @Schema(description = "사업자 등록번호")
    private String bussinessNumber;

    @Schema(description = "대표자 성명")
    private String primaryRepresentName1;

    @Schema(description = "상호 명")
    private String brandName;

}
