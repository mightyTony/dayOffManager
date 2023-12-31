package mightytony.sideproject.dayoffmanager.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static mightytony.sideproject.dayoffmanager.common.Constants.ACCESS_TOKEN_EXPIRED_TIME;
import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    //yml의 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * User의 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
     * Access Token : 인증된 사용자의 권한 정보와 만료 시간을 담고 있음
     * Refresh Token : Access Token 의 갱신을 위해 사용 됨
     */
    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME );
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresTime)
                .compact();

        //Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * @apiNote : Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     * 주어진 Access Token을 복호화하여 사용자의 인증 정보(Authentication)를 생성
     * 토큰 안의 Claims에서 권한 정보를 추출, User 객체를 생성하여 Authentication 객체로 변환
     * 과정
     * 1. Claims에서 권한 정보를 가져옴. 'auth' 클레임은 토큰에 저장된 권한 정보를 나타 냄
     * 2. 가져온 권한 정보를 SimpleGrantedAuthority 객체로 변환하여 컬렉션에 추가
     * 3. UserDetails 객체를 생성하여 주체, 권한 정보, 기타 필요 정보를 설정
     * 4. UsernamepasswordAuthenticationToken 객체를 생성해 주체와 권한 정보를 포함한 인증(Authentication) 객체를 생성
     */
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null) {
            // fixme : 예외처리 하드하게 되어있음.
            throw new RuntimeException("권한 정보가 없는 토큰 입니다.");

        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList(); //.collect(Collectors.toList());

        // UserDetails 객체 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails 구현한 class
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    /**
     * 클레임(Claims) : 토큰에서 사용할 정보의 조각
     * 주어진 Access Token을 복호화하고, 만료된 토큰일 경우도 Claims 반환
     * parseClaimsJws() 메서드가 JWT 토큰의 검증,파싱을 함.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 토큰 입니다", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰 입니다.",e);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰 입니다.",e);
        } catch (IllegalArgumentException e) {
            log.info("올바른 형식이 아니거나 토큰이 비어 있습니다.",e);
        }
        return false;
    }

    // accessToken 토큰 키를 통해 파싱
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // JWT를 통해 username + password 인증 수행.
}
