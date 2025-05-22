package com.pay.common.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
public class JwtUtil {

    private static final Key key = Keys.hmacShaKeyFor("secret-key-very-secure-secret-key".getBytes(StandardCharsets.UTF_8));

    public static String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("토큰 오류 :{}", e.getMessage());
            return false;
        }
    }
}