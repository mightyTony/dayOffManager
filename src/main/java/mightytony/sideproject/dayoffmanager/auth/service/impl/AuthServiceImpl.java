package mightytony.sideproject.dayoffmanager.auth.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.config.redis.RedisUtil;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    /**
     * authenticate() 메서드를 통해 Member 검증 진행
     */
    @Transactional(readOnly = true)
    @Override
    public JwtToken signIn(String userId, String password) {
        // ID 체크
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
        // 비번 체크
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new CustomException(ResponseCode.PASSWORD_INVALID);
        }
        // 1. username + password 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청 된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행 될 때 CustomUserDetailService
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // Redis 에 refresh token 저장
        redisUtil.saveRefreshToken(jwtToken.getRefreshToken(), authentication.getName());

        log.info("CONNECT : {} 님이 로그인 하였습니다.", authentication.getName());

        return jwtToken;
    }

    @Transactional
    @Override
    public void signUp(MemberCreateRequestDto req) {
        // 1. 이미 존재하는 지 체크
        if(memberRepository.existsMemberByUserIdAndPhoneNumberAndEmail(req.getUserId(), req.getPhoneNumber(), req.getEmail())){
           throw new CustomException(ResponseCode.User_Already_Existed);
        }
        // 2. 가입 (가입 할 땐 회사 등록을 안 함)
        Member member = Member.builder()
                .userId(req.getUserId())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .profileImage(req.getProfileImage())
                .build();

        memberRepository.save(member);

        log.info("JOIN: {}({}) 님이 회원 등록 하였습니다.", member.getUserId(), member.getName());
    }

    @Override
    @Transactional
    public void logOut(HttpServletRequest request) {
        // 1. 헤더에서 accessToken, refreshToken 가져오기 ( Authorization, Refresh 헤더에 저장)
        String accessToken = getAccessTokenFromRequest(request);

        // 2. Access Token 검증
        if(!jwtTokenProvider.validateToken(accessToken)) {
            throw new CustomException(ResponseCode.InvalidAccessToken);
        }

        // 3. Access Token 만료 확인
        if(jwtTokenProvider.isTokenExpired(accessToken)){
            throw new CustomException(ResponseCode.TokenExpiredJwtException);
        }

        // 4. 유저 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String username = authentication.getName();

        // 5. 리프레시 토큰 추출
        String refreshToken = getRefreshTokenFromCookie(request);

        // 6. RefreshToken redis 블랙리스트 추가
        if(refreshToken != null) {
            // 6-1 RefreshToken 이 이미 블랙리스트에 있는 가?
            if(redisUtil.isTokenInBlackList(refreshToken, username)){
                throw new CustomException(ResponseCode.AlreadyLogout);
            }

            // 로그
            //log.info("=======================refreshToken = {}", refreshToken);
            //log.info("========================username = {}", username);
            //
            redisUtil.setRefreshTokenAddToBlackList(refreshToken, "BL:" + username);
        }

        // 7. AccessToken redis 블랙리스트 추가
        if (accessToken != null) {
            redisUtil.setAccessTokenAddToBlackList(accessToken, "BL:" + username);
        }

        // 로그아웃 로그
        log.info("LOGOUT: {}님이 로그아웃 하였습니다.", username);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh"))
                    return cookie.getValue();
            }
        }
        return null;
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        // 1. 헤더 중 Authorization 헤더를 가져 옴
        String bearerToken = request.getHeader("Authorization");

        // 2. Authorization 헤더 value 가 Bearer로 시작 한다면 그 뒤 값 반환.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}