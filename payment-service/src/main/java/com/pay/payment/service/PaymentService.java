package com.pay.payment.service;

import com.pay.common.event.PaymentEvent;
import com.pay.payment.dto.PaymentRequest;
import com.pay.payment.event.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentProducer paymentProducer;

    public void processPayment(PaymentRequest request) {

        PaymentEvent event = PaymentEvent.builder()
                .email(request.email())
                .amount(request.amount())
                .build();

        paymentProducer.send(event);
    }
}
