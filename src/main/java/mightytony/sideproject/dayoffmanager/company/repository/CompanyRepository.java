package mightytony.sideproject.dayoffmanager.company.repository;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryCompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, QueryCompanyRepository {
    boolean existsByBusinessNumber(String businessNumber);

    Company findByBusinessNumber(String businessNumber);

}
