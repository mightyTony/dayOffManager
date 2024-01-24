package mightytony.sideproject.dayoffmanager.config;

import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtAuthenticationFilter;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.member.domain.MemberRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    // 비밀번호 암호화 방식이 여러 개 있지만 그 중에서 BCrypt 채택
    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCrypt Encoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/favicon.ico",
                "/swagger-ui/**",
                "/swagger-resource/**",
                "/error",
                "/v3/api-docs/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // REST API 이므로 base auth, csrf 보안을 사용하지 않음.
                .httpBasic((basic) -> basic.disable())
                .csrf((csrf) -> csrf.disable())
                // JWT 를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        // 해당하는 API에 대해서는 모든 사람 접속 허용
                        .requestMatchers("/","/members/sign-in","/swagger-ui/**","/company/*","/members/sign-up").permitAll()
                        // 해당하는 API에 대해서는 유저의 권한이 팀장, 관리자인 사람만 가능
                        .requestMatchers("/members/test").hasAnyRole(MemberRole.TEAM_LEADER.name(), MemberRole.ADMIN.name())
                        // 그 이외의 요청 API는 인증이 필요하다.
                        .anyRequest().authenticated()
                )
                // 내가 커스텀 한 필터인 Jwt 인증 필터를 UsernamePasswordAuthenticationFilter 의 앞에서 실행 하게 하겠다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
