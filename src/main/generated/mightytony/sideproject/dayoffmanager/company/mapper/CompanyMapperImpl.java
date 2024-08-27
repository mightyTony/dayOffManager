package mightytony.sideproject.dayoffmanager.company.mapper;

import javax.annotation.processing.Generated;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T01:00:00+0900",
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

        companyResponseDto.setBusinessNumber( entity.getBusinessNumber() );
        companyResponseDto.setStartDate( entity.getStartDate() );
        companyResponseDto.setPrimaryRepresentName1( entity.getPrimaryRepresentName1() );
        companyResponseDto.setBrandName( entity.getBrandName() );
        companyResponseDto.setDeleted( entity.getDeleted() );
        companyResponseDto.setDeleteDate( entity.getDeleteDate() );

        return companyResponseDto;
    }

    @Override
    public Company toEntity(CompanyResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Company.CompanyBuilder company = Company.builder();

        company.businessNumber( dto.getBusinessNumber() );
        company.startDate( dto.getStartDate() );
        company.primaryRepresentName1( dto.getPrimaryRepresentName1() );
        company.brandName( dto.getBrandName() );
        company.deleted( dto.getDeleted() );
        company.deleteDate( dto.getDeleteDate() );

        return company.build();
    }
}
