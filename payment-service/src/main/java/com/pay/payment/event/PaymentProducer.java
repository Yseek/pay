package com.pay.payment.event;

import com.pay.common.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    private final String TOPIC = "payment.pay";

    public void send(PaymentEvent event) {
        log.info("Kafka 이벤트 전송: {} {}", event.getEmail(), event.getAmount());
        kafkaTemplate.send(TOPIC, event);
    }
}
