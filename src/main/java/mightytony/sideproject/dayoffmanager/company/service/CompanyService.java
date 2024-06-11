package mightytony.sideproject.dayoffmanager.company.service;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyCreateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanySearchRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanySearchResponseDto;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

public interface CompanyService {
    boolean isDuplicate(String businessNumber);

    void save(CompanyCreateRequestDto req);

    List<Company> findAll();

    CompanyResponseDto findById(Long id);

    void updateCompany(CompanyUpdateRequestDto req);

    void deleteCompany(String brandName);

    CompanyResponseDto findByBrandName(String brandName);

    CompanySearchResponseDto searchCompany(CompanySearchRequestDto req) throws URISyntaxException;
}
