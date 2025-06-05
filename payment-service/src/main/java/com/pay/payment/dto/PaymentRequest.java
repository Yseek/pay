package com.pay.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public record PaymentRequest(
        @NotBlank String email,
        @NotNull @NumberFormat int amount
) {}