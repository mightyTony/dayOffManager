package mightytony.sideproject.dayoffmanager.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    //private final RedisUtil redisUtil;

    /**
     * @apiNote doFilter()
     * 1. resolveToken() 메서드를 사용하여 요청 헤더에서 JWT 토큰을 추출
     * 2. JwtTokenProvider의 validateToken() 메서드로 JWT 토큰 유효성 검증
     * 3. 토큰이 유효하면 JwtTokenProvider의 getAuthentication() 메서드로 인증 객체 가져와서 SecurityContext에 저장
     * => 요청을 처리하는 동안 인증 정보가 유지된다.
     * 4. filterChain.doFilter() 를 호출하여 다음 필터로 요청 전달
     *
     * @apiNote resolveToken()
     * 1. 주어진 HttpServletRequest에서 토큰 정보를 추출하는 역할
     * 2. "Authorization" 헤더에서 "Bearer" 접두사로 시작하는 토큰을 추출하여 반환
     */

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        // 1. Request Header에서 JWT 토큰 추출
//        String accessToken = resolveToken((HttpServletRequest) request);
//        String refreshToken = getRefreshTokenFromCookie((HttpServletRequest) request);
//
//        // 2. 접근 토큰 유효성 검사
//        // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
//        if(accessToken != null) {
//            // 2-1. 토큰 검증
//
//            if(jwtTokenProvider.validateToken(accessToken)){
//                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                throw new CustomException(ResponseCode.InvalidAccessToken);
//            }
//        }
//        // TODO refreshToken 검증
//        if(refreshToken != null){
//            //throw new CustomException(ResponseCode.RefreshTokenValidException);
//        }
//
//        // 4. 다음 필터로 이동
//        filterChain.doFilter(request,response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // 특정 URI에 대해 JWT 토큰 검사 건너뛰기
        if (requestURI.startsWith("/healthcheck") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-resource") ||
                requestURI.startsWith("/api/v1/auth")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 1. Request Header에서 JWT 토큰 추출
        String accessToken = resolveToken((HttpServletRequest) request);

        String refreshToken = getRefreshTokenFromCookie((HttpServletRequest) request);

        if(accessToken != null) {
            log.info("접근 토큰 : {}", accessToken);
        }

        try {
            // 2. 접근 토큰 유효성 검사
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
            if(accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                // 2-1. 토큰 검증
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // TODO refreshToken 검증
            else if(accessToken == null && refreshToken != null && jwtTokenProvider.validateToken(refreshToken)){
                //throw new CustomException(ResponseCode.RefreshTokenValidException);
                log.info("새 토큰 필요");
                String newAccessToken = authService.refreshAccessToken(request);
                accessToken = newAccessToken;
                Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("Authorization", "Bearer " + newAccessToken);
                //log.info("new Acc : {}", newAccessToken);
            }
            else {
                throw new CustomException(ResponseCode.InvalidAccessToken);
            }
        } catch (CustomException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        // 4. 다음 필터로 이동
        filterChain.doFilter(request,response);
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

    // Request Header에서 토큰 정보 추출
    public String resolveToken(HttpServletRequest request) {
        // 1. 헤더 중 Authorization 헤더를 가져 옴
        String bearerToken = request.getHeader("Authorization");

        //log.info("In request token : {}", bearerToken);

        // 2. Authorization 헤더 value 가 Bearer로 시작 한다면 그 뒤 값 반환.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }
        return null;
    }

}
