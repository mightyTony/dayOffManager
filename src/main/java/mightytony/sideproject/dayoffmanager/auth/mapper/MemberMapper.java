package mightytony.sideproject.dayoffmanager.auth.mapper;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberResponseDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberUpdateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.brandName")
    MemberResponseDto toDTO(Member member);

    Member toEntity(MemberResponseDto dto);

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.brandName")
    MemberLoginResponseDto toLoginDTO(Member member);

//    @Mapping(target = "companyId", source = "company.id")
//    @Mapping(target = "companyName", source = "company.brandName")
    MemberUpdateResponseDto toUpdateDTO(MemberLoginResponseDto loginResponseDto);
}
