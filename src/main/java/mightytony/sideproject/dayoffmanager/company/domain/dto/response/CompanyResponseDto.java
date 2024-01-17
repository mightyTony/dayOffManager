package mightytony.sideproject.dayoffmanager.company.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.company.domain.Company;

@Data
public class CompanyResponseDto {

    private String businessNumber;
    private String startDate;
    private String primaryRepresentName1;
    private String brandName;
}
