package mightytony.sideproject.dayoffmanager.member.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.config.redis.RedisUtil;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import mightytony.sideproject.dayoffmanager.member.domain.Member;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.member.repository.MemberRepository;
import mightytony.sideproject.dayoffmanager.member.service.MemberService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
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

        //log.info("member save = {}", memberRepository.save(member));
    }

    @Override
    @Transactional
    public void logOut(HttpServletRequest request) {
        // FIXME 나중엔 이거 리프레시 토큰 따로 쿠키에 저장 하고 싶다..
        // 1. 헤더에서 accessToken, refreshToken 가져오기 ( Authorization, Refresh 헤더에 저장)
        String accessToken = getTokenFromRequest(request);

        // 2. Access Token 검증
        if(!jwtTokenProvider.validateToken(accessToken)) {
            throw new CustomException(ResponseCode.InvalidAccessToken);
        }

        // 3. Access Token 만료 확인
        if(jwtTokenProvider.isTokenExpired(accessToken)){
            throw new CustomException(ResponseCode.TokenExpiredJwtException);
        }

        // 4. RefreshToken 검증
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String username = authentication.getName();
        log.info("@@@@@@@@@@@@@@@@@@@@username = {}", username);

        // 5. RefreshToken redis 삭제

        // 6. accesstoken redis 블랙리스트 추가

        // 클라이언트에 응답
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        // 1. accessToken
        String accessToken = request.getHeader("Authorization");
        System.out.println("accessToken = " + accessToken);

        /*
        // 2. refreshToken
        String refreshToken = request.getHeader("Refresh");

        JwtToken jwtToken = JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        */

        return accessToken;
    }

}
