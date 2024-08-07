package mightytony.sideproject.dayoffmanager.auth.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-06T01:55:40+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberResponseDto toDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDto memberResponseDto = new MemberResponseDto();

        memberResponseDto.setCompanyId( memberCompanyId( member ) );
        memberResponseDto.setCompanyName( memberCompanyBrandName( member ) );
        memberResponseDto.setUserId( member.getUserId() );
        memberResponseDto.setName( member.getName() );
        memberResponseDto.setEmail( member.getEmail() );
        memberResponseDto.setPhoneNumber( member.getPhoneNumber() );
        memberResponseDto.setProfileImage( member.getProfileImage() );
        List<MemberRole> list = member.getRoles();
        if ( list != null ) {
            memberResponseDto.setRoles( new ArrayList<MemberRole>( list ) );
        }
        memberResponseDto.setEmployeeNumber( member.getEmployeeNumber() );
        memberResponseDto.setResignationDate( member.getResignationDate() );
        memberResponseDto.setDayOffCount( member.getDayOffCount() );
        memberResponseDto.setStatus( member.getStatus() );

        return memberResponseDto;
    }

    @Override
    public Member toEntity(MemberResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.userId( dto.getUserId() );
        member.name( dto.getName() );
        member.email( dto.getEmail() );
        member.phoneNumber( dto.getPhoneNumber() );
        member.profileImage( dto.getProfileImage() );
        List<MemberRole> list = dto.getRoles();
        if ( list != null ) {
            member.roles( new ArrayList<MemberRole>( list ) );
        }
        member.employeeNumber( dto.getEmployeeNumber() );
        member.resignationDate( dto.getResignationDate() );
        member.dayOffCount( dto.getDayOffCount() );
        member.status( dto.getStatus() );

        return member.build();
    }

    @Override
    public MemberLoginResponseDto toLoginDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto();

        memberLoginResponseDto.setCompanyId( memberCompanyId( member ) );
        memberLoginResponseDto.setCompanyName( memberCompanyBrandName( member ) );
        memberLoginResponseDto.setUserId( member.getUserId() );
        memberLoginResponseDto.setName( member.getName() );
        memberLoginResponseDto.setEmail( member.getEmail() );
        memberLoginResponseDto.setPhoneNumber( member.getPhoneNumber() );
        memberLoginResponseDto.setProfileImage( member.getProfileImage() );
        List<MemberRole> list = member.getRoles();
        if ( list != null ) {
            memberLoginResponseDto.setRoles( new ArrayList<MemberRole>( list ) );
        }
        memberLoginResponseDto.setEmployeeNumber( member.getEmployeeNumber() );
        memberLoginResponseDto.setDayOffCount( member.getDayOffCount() );
        memberLoginResponseDto.setStatus( member.getStatus() );

        return memberLoginResponseDto;
    }

    private Long memberCompanyId(Member member) {
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

    private String memberCompanyBrandName(Member member) {
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
