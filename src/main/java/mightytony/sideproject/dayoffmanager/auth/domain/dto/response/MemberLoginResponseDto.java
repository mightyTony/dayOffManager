package mightytony.sideproject.dayoffmanager.auth.domain.dto.response;

import lombok.Data;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class MemberLoginResponseDto {
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
    private double dayOffCount;
    private MemberStatus status;
    private LocalDate hireDate;

    public String getHireDate() {
        if (hireDate != null) {
            return hireDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }
}
