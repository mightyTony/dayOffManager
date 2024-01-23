package mightytony.sideproject.dayoffmanager.company.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "업체 수정 dto")
@Data
public class CompanyUpdateRequestDto {

    private Long id;

    @Schema(description = "사업자 등록번호")
    @NotNull
    private String bussinessNumber;

    @Schema(description = "대표자 성명")
    @NotNull
    private String primaryRepresentName1;

    @Schema(description = "상호 명")
    @NotNull
    private String brandName;

}

/*
    @Scheme : Swagger 에서 해당 부분 설명 추가 해주는 기능.
 */