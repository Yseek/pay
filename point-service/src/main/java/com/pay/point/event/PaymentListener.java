package com.pay.point.event;

import com.pay.common.event.PaymentEvent;
import com.pay.point.service.PointService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PointService pointService;

    @KafkaListener(topics = "payment.pay", groupId = "point-service")
    @Transactional
    public void consumePayment(PaymentEvent event) {
        pointService.decreasePoint(event);
    }
}
