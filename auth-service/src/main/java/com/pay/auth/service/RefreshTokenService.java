package com.pay.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final long REFRESH_EXPIRATION_SECONDS = 60 * 60 * 24 * 7; // 7일

    public void save(String email, String refreshToken) {
        redisTemplate.opsForValue().set("RT:" + email, refreshToken, Duration.ofSeconds(REFRESH_EXPIRATION_SECONDS));
    }

    public String get(String email) {
        return redisTemplate.opsForValue().get("RT:" + email);
    }

    public void delete(String email) {
        redisTemplate.delete("RT:" + email);
    }
}
