package mightytony.sideproject.dayoffmanager.company.service;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyCreateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;

import java.util.List;

public interface CompanyService {
    boolean isDuplicate(String businessNumber);

    Company save(CompanyCreateRequestDto req);

    List<Company> findAll();

    CompanyResponseDto findById(Long id);

    void updateCompany(CompanyUpdateRequestDto req);

    void deleteCompany(Long id);

    CompanyResponseDto findByCondition(CompanyRequestDto req);
}
