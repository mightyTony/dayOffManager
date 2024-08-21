package mightytony.sideproject.dayoffmanager.auth.domain.dto.response;

import lombok.Data;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.company.domain.Company;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemberResponseDto {
    //private Long id;
    private String userId;
    private Long companyId;
    private String companyName;
    private Long departmentId;
    private String departmentName;
    private String name;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private List<MemberRole> roles;
    private String employeeNumber;
    private LocalDate resignationDate;
    private double dayOffCount;
    private MemberStatus status;

}


