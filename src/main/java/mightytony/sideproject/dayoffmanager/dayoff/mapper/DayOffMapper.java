package mightytony.sideproject.dayoffmanager.dayoff.mapper;

import mightytony.sideproject.dayoffmanager.auth.mapper.MemberMapper;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface DayOffMapper {

    @Mapping(source = "member.userId", target = "userId")
    @Mapping(source = "member.company.id", target = "companyId")
    @Mapping(source = "member.company.brandName", target = "companyName")
    //
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    DayOffApplyResponseDto toDTO(DayOff dayOff);
}
