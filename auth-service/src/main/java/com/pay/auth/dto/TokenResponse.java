package com.pay.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
