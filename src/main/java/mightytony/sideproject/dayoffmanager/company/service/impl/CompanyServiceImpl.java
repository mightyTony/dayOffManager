package mightytony.sideproject.dayoffmanager.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CreateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.UpdateCompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.mapper.CompanyMapper;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

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

    /**
     * @apiNote : 모든 업체 조회
     * @return
     */
    @Override
    public List<Company> findAll() {
        List<Company> allCompany = companyRepository.findAll();
        log.info("allCompany.toString() = {}", allCompany.stream().collect(Collectors.toList()).toString());

        return allCompany;
    }

    /**
     * @apiNote : 해당 아이디 업체 조회
     * @param id
     * @return
     */
    @Override
    public CompanyResponseDto findById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("그런 아이디 없는디"));

        return companyMapper.toDTO(company);
    }

    @Transactional
    @Override
    public void updateCompany(UpdateCompanyRequestDto req) {
        // 1. 해당 업체 있는지 존재 확인

        // 2. 수정
    }

}
