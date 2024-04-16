package mightytony.sideproject.dayoffmanager.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyCreateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.mapper.CompanyMapper;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Transactional(readOnly = false)
    @Override
    public Company save(CompanyCreateRequestDto req) {
        // 1. 중복 인지 체크
        boolean isDuplicate = isDuplicate(req.getBusinessNumber());

        if (isDuplicate) {
            throw new CustomException(ResponseCode.BUSINESSNUMBER_IS_ALREADY_EXIST);
        }

        // 2. Company req Dto -> Company
        Company NewCompany = Company.builder()
                .businessNumber(req.getBusinessNumber())
                .startDate(req.getStartDate())
                .primaryRepresentName1(req.getPrimaryRepresentName1())
                .brandName(req.getBrandName())
                .build();

        log.info("기업 등록 : COMPANY : {}", NewCompany.getBrandName());
        // 3. Save And Return
        return companyRepository.save(NewCompany);
    }

    /**
     * @apiNote : 모든 업체 조회
     * @return
     */
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    @Override
    public CompanyResponseDto findById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COMPANY));

        return companyMapper.toDTO(company);
    }

    /**
     * 업체 상호명 수정
     * @param req
     */
    @Transactional(readOnly = false)
    @Override
    public void updateCompany(CompanyUpdateRequestDto req) {
        // 1. 해당 업체 있는지 존재 확인
        //Company company = companyRepository.findByBusinessNumber(req.getBussinessNumber());
        Company company = companyRepository.findById(req.getId())
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        // 2. 수정
        company.update(req);
        // 3. 저장
        companyRepository.save(company);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCompany(String brandName) {
        // 1. 해당 업체 있는지 확인
        Company company = companyRepository.findByBrandName(brandName);

        if(company == null) {
            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
        }

        // 2. 삭제 (soft delete)
//        company.setDeleted(true);
//        company.setDeleteDate(LocalDate.now());
        company.delete(brandName);
        companyRepository.save(company);
        // Log
        log.info("기업 삭제 : {}", company.getBrandName());
    }

    @Override
    public CompanyResponseDto findByBrandName(String brandName) {

        Company foundCompany = companyRepository.findByBrandName(brandName);
        CompanyResponseDto dto = companyMapper.toDTO(foundCompany);
        return dto;
    }

//    @Override
//    public CompanyResponseDto findByCondition(CompanyRequestDto req) {
//        // 1. 기업 조회
//        Company company = companyRepository.findByConditions(req);
//
//        log.info("SM company = {}", company);
//        if (company.getBusinessNumber() == null){
//            throw new CustomException(ResponseCode.NOT_FOUND_COMPANY);
//        }
//        log.info("SM dto = {}", companyMapper.toDTO(company));
//        return companyMapper.toDTO(company);
//    }


}
