package mightytony.sideproject.dayoffmanager.company.repository;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryCompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, QueryCompanyRepository {
    boolean existsByBusinessNumber(String businessNumber);

    Company findByBusinessNumber(String businessNumber);

    Company findByBrandName(String brandName);

    Company deleteByBrandName(String brandName);
}
