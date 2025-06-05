package com.pay.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record UserProfileResponse(
        @Schema(description = "사용자 이메일", example = "user@example.com")
        String email,

        @Schema(description = "사용자 닉네임", example = "홍길동")
        String nickname,

        @Schema(description = "가입 일시", example = "2023-01-01T00:00:00")
        LocalDateTime joinedAt
) {}