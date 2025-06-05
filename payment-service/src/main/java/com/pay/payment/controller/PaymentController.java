package com.pay.payment.controller;

import com.pay.common.response.CommonResponse;
import com.pay.payment.dto.PaymentRequest;
import com.pay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<CommonResponse<String>> pay(@Valid @RequestBody PaymentRequest request) {
        paymentService.processPayment(request);
        return ResponseEntity.ok(CommonResponse.success("결제 완료"));
    }
}
