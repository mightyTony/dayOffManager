package mightytony.sideproject.dayoffmanager.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.mapper.MemberMapper;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.config.CustomUserDetailsService;
import mightytony.sideproject.dayoffmanager.config.redis.RedisUtil;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static mightytony.sideproject.dayoffmanager.common.Constants.ACCESS_TOKEN_EXPIRED_TIME;
import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final RedisUtil redisUtil;
    @Value("${jwt.issuer}")
    private String issuer;

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;

    //yml 의 secret 값 가져와서 key 에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, RedisUtil redisUtil, CustomUserDetailsService customUserDetailsService) {
        this.redisUtil = redisUtil;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        //FIXME
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * User 의 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
     * Access Token : 인증된 사용자의 권한 정보와 만료 시간을 담고 있음
     * Refresh Token : Access Token 의 갱신을 위해 사용 됨
     */
    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        //log.info("!!!!!!!!!!!!!authorities = {}", authorities);

        long now = (new Date()).getTime();
        Date nowdate = new Date();
        // Access Token 생성
        Date accessTokenExpiresTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME );
//        log.info("############## now = {}, ACCESS_TOKEN_EXPIRED_TIME = {}", now, ACCESS_TOKEN_EXPIRED_TIME);
//        log.info("############## accessTokenExpiresTime = {}", accessTokenExpiresTime);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuer(issuer)
                .setIssuedAt(nowdate)
                // TODO
                .addClaims(addUserInformation(authentication))
                .setExpiration(accessTokenExpiresTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();


        //Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuer(issuer)
                .setIssuedAt(nowdate)
                .addClaims(addUserInformation(authentication))
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 추가 정보 claim 에 담기
    private Map<String, Object> addUserInformation(Authentication authentication) {
        Map<String, Object> addToClaim = new HashMap<>();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 추가 정보 가져오기
        if (userDetails instanceof Member) {
            Member member = (Member) userDetails;
            addToClaim.put("email", member.getEmail());
            addToClaim.put("userId", member.getUserId());
        }
        // 추가 할 사항이 있다면 여기에

        return addToClaim;
    }


    /**
     * @apiNote : Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     * 주어진 Access Token 을 복호화하여 사용자의 인증 정보(Authentication)를 생성
     * 토큰 안의 Claims 에서 권한 정보를 추출, User 객체를 생성하여 Authentication 객체로 변환
     * 과정
     * 1. Claims 에서 권한 정보를 가져옴. 'auth' 클레임은 토큰에 저장된 권한 정보를 나타 냄
     * 2. 가져온 권한 정보를 SimpleGrantedAuthority 객체로 변환하여 컬렉션에 추가
     * 3. UserDetails 객체를 생성하여 주체, 권한 정보, 기타 필요 정보를 설정
     * 4. Username passwordAuthenticationToken 객체를 생성해 주체와 권한 정보를 포함한 인증(Authentication) 객체를 생성
     */
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);
        if(claims.get("auth") == null) {
            throw new CustomException(ResponseCode.TokenMissingAuthorities);

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
            //token = token.trim();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("유효하지 않은 시그니쳐, 토큰 형식 : [{}] : {} ", token, e.getMessage());
            throw new CustomException(ResponseCode.TokenSecurityExceptionOrMalformdJwtException);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 토큰 : [{}] : {}", token, e.getMessage());
            throw new CustomException(ResponseCode.TokenExpiredJwtException);
        } catch (UnsupportedJwtException e) {
            log.warn("지원하지않는 토큰 : [{}] : {}", token, e.getMessage());
            throw new CustomException(ResponseCode.TokenUnsupportedJwtException);
        } catch (IllegalArgumentException e) {
            log.warn("토큰 형식이 맞지 않거나 리프레시 토큰 만료 : [{}] : {}", token, e.getMessage());
            throw new CustomException(ResponseCode.TokenIllegalArgumentException);
        }
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



    public boolean isTokenExpired(String accessToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰 예외 처리
            throw new CustomException(ResponseCode.TokenExpiredJwtException);
        }
    }

    public long getTokenExpiredTime(String token) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Expiration() -> Date
            return claims.getExpiration().getTime();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ResponseCode.TokenExpiredJwtException);
        }
    }

    //FIXME
    //구조적으로 안됨.
//    public boolean validateRefreshToken(String refreshToken) {
//        try {
//            Claims claims = parseClaims(refreshToken);
//            log.info("claims.getSubject() : {}", claims.getSubject());
//            // Redis or Database에서 refreshToken 검증
//            String storedRefreshToken = (String) redisUtil.getRefreshToken(claims.getSubject());
//            log.info("storedRefreshToken: {} ", storedRefreshToken);
//            log.info("equals : {}, claims Expiration : {}", refreshToken.equals(storedRefreshToken), claims.getExpiration().before(new Date()));
//            return refreshToken.equals(storedRefreshToken) && !claims.getExpiration().before(new Date());
//        } catch (Exception e) {
//            log.error("리프레시 토큰 검증 실패: 입력 값 = {}, 에러 = {}", refreshToken, e.toString());
//            return false;
//        }
//    }

    public JwtToken refreshAccessToken(String refreshToken) {

//        if(!validateRefreshToken(refreshToken)){
//            throw new CustomException(ResponseCode.RefreshTokenValidException);
//        }

        Claims claims = parseClaims(refreshToken);
        if(claims.getExpiration().before(new Date())) {
            throw new CustomException(ResponseCode.REFRESH_TOKEN_IS_EXPIRED);
        }

        String userId = claims.getSubject();

//        Member member = memberRepository.findByUserId(userId)
//                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
//
//        if(member.getStatus() != MemberStatus.APPROVED){
//            throw new CustomException(ResponseCode.NOT_FOUND_USER);
//        }

        MemberLoginResponseDto cachedUserInformation = getCachedUserInformation(userId);

        if(cachedUserInformation.getStatus() != MemberStatus.APPROVED){
            throw new CustomException(ResponseCode.NOT_FOUND_USER);
        }

        Authentication authentication = customUserDetailsService.getUserAuthentication(userId);

        String accessToken = generateAccessToken(authentication);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken) // 기존 리프레시 토큰 유지
                .build();
    }

    //@Cacheable(value = "userInformation", key = "#userId")
    public MemberLoginResponseDto getCachedUserInformation(String userId) {
        // 캐시에서 유저 조회
        MemberLoginResponseDto cachedUserInformation = redisUtil.getUserFromCache(userId);

        if(cachedUserInformation != null) {
            //fixme
            //log.info("캐시 히트: 유저 정보 = {}", cachedUserInformation);
        }
        else {
            log.info("캐시에서 사용자 정보를 찾을 수 없음, DB 조회 : {}", userId);
            Member member = memberRepository.findByUserId(userId)
                    .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));

            if(member.getStatus() != MemberStatus.APPROVED){
                throw new CustomException(ResponseCode.NOT_FOUND_USER);
            }

            // DB에서 조회한 유저 정보를 DTO로 변환 후 캐시에 저장
            cachedUserInformation = memberMapper.toLoginDTO(member);
            redisUtil.saveUser(userId,cachedUserInformation);
        }
        return cachedUserInformation;

    }

    private String generateAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpiresTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);
        Date nowdate = new Date();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuer(issuer)
                .setIssuedAt(nowdate)
                // TODO
                .addClaims(addUserInformation(authentication))
                .setExpiration(accessTokenExpiresTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
