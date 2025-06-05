package com.pay.user.config;

import com.pay.common.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    private static final String[] PERMIT_ALL_PATTERNS = {
        // Swagger 관련 경로
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
        "/actuator/**",
        "/docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PERMIT_ALL_PATTERNS).permitAll()  // Swagger 경로는 인증 없이 접근 가능
                .anyRequest().authenticated()  // 나머지 모든 요청은 인증 필요
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}