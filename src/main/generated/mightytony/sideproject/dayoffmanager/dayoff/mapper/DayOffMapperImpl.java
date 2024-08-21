package mightytony.sideproject.dayoffmanager.dayoff.mapper;

import javax.annotation.processing.Generated;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T16:23:03+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class DayOffMapperImpl implements DayOffMapper {

    @Override
    public DayOffApplyResponseDto toDTO(DayOff dayOff) {
        if ( dayOff == null ) {
            return null;
        }

        DayOffApplyResponseDto dayOffApplyResponseDto = new DayOffApplyResponseDto();

        dayOffApplyResponseDto.setUserId( dayOffMemberUserId( dayOff ) );
        dayOffApplyResponseDto.setCompanyId( dayOffMemberCompanyId( dayOff ) );
        dayOffApplyResponseDto.setCompanyName( dayOffMemberCompanyBrandName( dayOff ) );
        dayOffApplyResponseDto.setType( dayOff.getType() );
        dayOffApplyResponseDto.setDuration( dayOff.getDuration() );
        dayOffApplyResponseDto.setStatus( dayOff.getStatus() );

        return dayOffApplyResponseDto;
    }

    private String dayOffMemberUserId(DayOff dayOff) {
        if ( dayOff == null ) {
            return null;
        }
        Member member = dayOff.getMember();
        if ( member == null ) {
            return null;
        }
        String userId = member.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private Long dayOffMemberCompanyId(DayOff dayOff) {
        if ( dayOff == null ) {
            return null;
        }
        Member member = dayOff.getMember();
        if ( member == null ) {
            return null;
        }
        Company company = member.getCompany();
        if ( company == null ) {
            return null;
        }
        Long id = company.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dayOffMemberCompanyBrandName(DayOff dayOff) {
        if ( dayOff == null ) {
            return null;
        }
        Member member = dayOff.getMember();
        if ( member == null ) {
            return null;
        }
        Company company = member.getCompany();
        if ( company == null ) {
            return null;
        }
        String brandName = company.getBrandName();
        if ( brandName == null ) {
            return null;
        }
        return brandName;
    }
}
