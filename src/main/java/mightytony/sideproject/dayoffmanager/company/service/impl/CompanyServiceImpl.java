package mightytony.sideproject.dayoffmanager.company.service.impl;

import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CreateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isDuplicate(String businessNumber) {
        return companyRepository.existsByBusinessNumber(businessNumber);
    }

    @Transactional
    @Override
    public Company save(CreateCompanyRequestDto req) {
        // 1. Company req Dto -> Company
        Company NewCompany = Company.builder()
                .businessNumber(req.getBusinessNumber())
                .startDate(req.getStartDate())
                .primaryRepresentName1(req.getPrimaryRepresentName1())
                .brandName(req.getBrandName())
                .build();

        // 2. Save And Return
        return companyRepository.save(NewCompany);
    }

    @Override
    public List<Company> findAll() {
        //List<Company> allCompany = companyRepository.findAll();
        return companyRepository.findAll();
    }
}
