package com.pay.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key = Keys.hmacShaKeyFor("secret-key-very-secure-secret-key".getBytes(StandardCharsets.UTF_8));
    private final long EXPIRATION = 1000L * 60 * 15;
    private final long REFRESH_EXPIRATION_MILLIS  = 1000L * 60 * 60 * 24 * 7; // 7일

    public String createToken(String email) {
        Date now = new Date();
        Date expriy = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expriy)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }
}
