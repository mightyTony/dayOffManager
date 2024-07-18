package mightytony.sideproject.dayoffmanager.company.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyCreateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanySearchRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanySearchResponseDto;
import mightytony.sideproject.dayoffmanager.company.mapper.CompanyMapper;
import mightytony.sideproject.dayoffmanager.company.repository.CompanyRepository;
import mightytony.sideproject.dayoffmanager.company.service.CompanyService;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${API.URL}")
    private String BASE_URL;
    @Value("${API.SERVICE_KEY}")
    private String SERVICE_KEY;

    @Override
    public boolean isDuplicate(String businessNumber) {
        return companyRepository.existsByBusinessNumber(businessNumber);
    }

    @Transactional(readOnly = false)
    @Override
    public void save(CompanyCreateRequestDto req) {
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
        companyRepository.save(NewCompany);
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

    @Override
    public CompanySearchResponseDto searchCompany(CompanySearchRequestDto req) throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> body = new HashMap<>();
        body.put("businesses", Arrays.asList(req));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body,headers);

        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY;
        URI uri = new URI(url);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);

            if(response.getStatusCode().is2xxSuccessful()) {
                CompanySearchResponseDto responsedto = objectMapper.readValue(response.getBody(), CompanySearchResponseDto.class);

                return responsedto;
            } else {
                log.error("API 호출 실패, 상태 코드 : {}, 응답 본문 : {}", response.getStatusCode(), response.getBody());
                throw new CustomException(ResponseCode.ERROR_PROCESSING_REQUEST);
            }
        } catch (HttpClientErrorException ex) {
            log.error("API 호출 중 에러 발생 : {}", ex.getResponseBodyAsString());
            throw new CustomException(ResponseCode.ERROR_PROCESSING_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
