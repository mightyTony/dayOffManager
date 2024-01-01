package mightytony.sideproject.dayoffmanager.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtToken {
    // JwtToken DTO

    private String grantType; // jwt 인증 타입 ex) Authorization: Bearer <access_token>
    private String accessToken;
    private String refreshToken;
}
