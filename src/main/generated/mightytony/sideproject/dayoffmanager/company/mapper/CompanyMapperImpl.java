package mightytony.sideproject.dayoffmanager.company.mapper;

import javax.annotation.processing.Generated;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-19T14:54:14+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyResponseDto toDTO(Company entity) {
        if ( entity == null ) {
            return null;
        }

        CompanyResponseDto companyResponseDto = new CompanyResponseDto();

        return companyResponseDto;
    }

    @Override
    public Company toEntity(CompanyResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        String businessNumber = null;
        String startDate = null;
        String primaryRepresentName1 = null;
        String brandName = null;

        Company company = new Company( businessNumber, startDate, primaryRepresentName1, brandName );

        return company;
    }
}
