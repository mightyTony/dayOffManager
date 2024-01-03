package mightytony.sideproject.dayoffmanager.config;

import ch.qos.logback.classic.BasicConfigurator;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .httpBasic((basic) -> basic.disable())
//                .csrf((csrf) -> csrf.disable())
//                .sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(a ->
//                        a.requestMatchers("/members/sign-in").permitAll()
//                                .requestMatchers()
//                        )
//
//
//
//
//                .build();
//    }
}
