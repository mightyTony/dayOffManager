package mightytony.sideproject.dayoffmanager.company.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.company.domain.Company;

import java.time.LocalDate;

@Data
public class CompanyResponseDto {

    private String businessNumber;
    private String startDate;
    private String primaryRepresentName1;
    private String brandName;
    private Boolean deleted = Boolean.FALSE;
    private LocalDate deleteDate;
}
