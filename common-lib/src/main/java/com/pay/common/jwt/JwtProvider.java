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
    private final long EXPIRATION = 1000 * 60 * 60;

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

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
