package com.pay.user.dto;

import java.time.LocalDateTime;

public record UserProfileResponse(
        String email,
        String nickname,
        LocalDateTime joinedAt
) {}
