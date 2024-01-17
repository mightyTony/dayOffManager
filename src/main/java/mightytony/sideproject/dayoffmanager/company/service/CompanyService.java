package mightytony.sideproject.dayoffmanager.company.service;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CreateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.UpdateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;

import java.util.List;

public interface CompanyService {
    boolean isDuplicate(String businessNumber);

    Company save(CreateCompanyRequestDto req);

    List<Company> findAll();

    CompanyResponseDto findById(Long id);

    void updateCompany(UpdateCompanyRequestDto req);
}
