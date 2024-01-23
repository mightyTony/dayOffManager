package mightytony.sideproject.dayoffmanager.member.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter@Setter
public class MemberLoginRequestDto {

    @JsonProperty("user_id")
    private String userId;
    private String password;
}
