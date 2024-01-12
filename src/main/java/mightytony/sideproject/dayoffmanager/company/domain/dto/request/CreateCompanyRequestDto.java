package mightytony.sideproject.dayoffmanager.company.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 회사 등록 요청을 위한 DTO
 */
@Data
public class CreateCompanyRequestDto {

    //
    @NotNull
    @Size(min = 10, max = 10, message = "사업자등록번호는 10자리여야 합니다.")
    private String businessNumber;

    @Pattern(regexp = "^[0-9]{8}$", message = "개업일자는 YYYYMMDD 포맷이어야 합니다.")
    private String startDate;

    @NotBlank(message = "대표자 성명이 비어있습니다.")
    private String primaryRepresentName1;

    @NotBlank(message = "상표가 비어있습니다.")
    private String brandName;
}
