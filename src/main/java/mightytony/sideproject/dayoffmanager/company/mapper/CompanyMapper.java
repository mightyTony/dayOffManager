package mightytony.sideproject.dayoffmanager.company.mapper;

import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.response.CompanyResponseDto;
import org.mapstruct.Mapper;

//StudentMapper.java
//@Mapper를 이용하여 mapper임을 알려주고, MapStruct를 이용해 코드를 generate해야 함을 알려준다.
//componentModel="spring"을 통해서 spring container에 Bean으로 등록 해 준다.
//unmappedTargetPolicy IGNORE 만약, target class에 매핑되지 않는 필드가 있으면, null로 넣게 되고, 따로 report하지 않는다.
//unmappedTargetPolicy Error -> 매핑 되지 않은 필드 있을 시 에러 뜨게 함 .
@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // mapStruct가 코드를 만들 메소드를 선언 해준다.
    // 아래 메소드는 Company 클래스를 받아, CompanyDTO에 매핑하여 리턴하는 함수 이다.
    CompanyResponseDto toDTO(Company entity);

    Company toEntity(CompanyResponseDto dto);
}
