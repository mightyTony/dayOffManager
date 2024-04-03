package mightytony.sideproject.dayoffmanager.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 1. Request Header에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        // 2. 토큰 유효성 검사
        // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
        if(token != null) {
            // 2-1. 토큰 검증
            if(jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new CustomException(ResponseCode.InvalidAccessToken);
            }
        }

        // 3. 다음 필터로 이동
        filterChain.doFilter(request,response);
    }

    // Request Header에서 토큰 정보 추출
    public String resolveToken(HttpServletRequest request) {
        // 1. 헤더 중 Authorization 헤더를 가져 옴
        String bearerToken = request.getHeader("Authorization");

        // 2. Authorization 헤더 value 가 Bearer로 시작 한다면 그 뒤 값 반환.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
