package com.pay.point.security;

import com.pay.common.jwt.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String token = extractJwt(request);

        if (token == null || !JwtUtil.validate(token)) {
            log.warn("토큰 누락 또는 유효하지 않음");
            ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT가 유효하지 않습니다.");
            return;
        }

        String email = JwtUtil.extractEmail(token);
        request.setAttribute("userEmail", email);
        log.info("user email: {}", email);

        chain.doFilter(req, res);
    }

    private String extractJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }
}