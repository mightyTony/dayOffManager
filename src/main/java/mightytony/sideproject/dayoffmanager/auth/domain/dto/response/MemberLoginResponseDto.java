package mightytony.sideproject.dayoffmanager.auth.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;

import java.time.LocalDate;

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
    private MemberRole role;
    private String employeeNumber;
    private double dayOffCount;
    private MemberStatus status;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate hireDate;

//    public String getHireDate() {
//        if (hireDate != null) {
//            return hireDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        }
//        return null;
//    }
}
