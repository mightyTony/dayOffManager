package mightytony.sideproject.dayoffmanager.company.repository.query;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyRequestDto;

public interface QueryCompanyRepository {

    Company findByConditions(CompanyRequestDto req);
}
