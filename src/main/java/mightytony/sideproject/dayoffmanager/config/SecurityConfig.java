package mightytony.sideproject.dayoffmanager.config;

import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtAccessDeniedHandler;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtAuthenticationEntryPoint;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtAuthenticationFilter;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.config.redis.RedisUtil;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    //private final RedisUtil redisUtil;

    // 비밀번호 암호화 방식이 여러 개 있지만 그 중에서 BCrypt 채택
    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCrypt Encoder
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
////                .requestMatchers("/api/docs/**")
//                .requestMatchers(
//                "/favicon.ico",
//                "/swagger-ui/**",
//                "/swagger-resource/**",
//                "/error",
//                "/v3/api-docs/**")
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors((cors) -> cors.configurationSource(corsConfiguration()))
                // REST API 이므로 base auth, csrf 보안을 사용하지 않음.
                .httpBasic((basic) -> basic.disable())
                .csrf((csrf) -> csrf.disable())
                // JWT 를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/favicon.ico","/swagger-ui/**","/swagger-resource/**","/error","/v3/api-docs/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                )
                .authorizeHttpRequests((authorize) -> authorize
                        // 해당하는 API에 대해서는 모든 사람 접속 허용
                        // FIXME : master 나중에 막아야함
                        //.requestMatchers("/",
                                // "/api/v1/auth/login",
                                // "/swagger-ui/**",
                                // "/api/v1/auth/join",
                                // "/api/v1/auth/logout",
                                // "/api/v1/auth/master",
                                // "/api/v1/auth/refresh",
                                // ).permitAll()
                        // 해당하는 API에 대해서는 유저의 권한이 팀장, 관리자인 사람만 가능
                        //.requestMatchers("/api/v1/auth/test").hasAnyRole(MemberRole.TEAM_LEADER.name(), MemberRole.ADMIN.name())
                        // Master
                        //.requestMatchers("/api/v1/master/**").hasRole(MemberRole.MASTER.name())
                        // 그 이외의 요청 API는 인증이 필요하다.
                        // FIXME: 추후 막기
                        .requestMatchers("/","/api/v1/auth/**","/swagger-ui/**").permitAll()
                        .anyRequest().permitAll()
                        //.anyRequest.().permitAll()//.authenticated()
                )
                .exceptionHandling((except) -> except
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                // 내가 커스텀 한 필터인 Jwt 인증 필터를 UsernamePasswordAuthenticationFilter 의 앞에서 실행 하게 하겠다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // FIXME : https://wnwngus.tistory.com/65 참고
    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();

        // TODO : 프론트 개발 후, 프론트 사이트: 프론트 포트 설정
        //config.addAllowedOrigin("{hostURL:frontEndPort}");
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        // FIXME 배포 시 아래 삭제
        //config.addAllowedOrigin("https://localhost:3000"); // vue project 3000 port
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        // 헤더 허용
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", config);
        return src;
    }

}
