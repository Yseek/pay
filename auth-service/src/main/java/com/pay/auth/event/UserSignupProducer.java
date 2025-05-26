package com.pay.auth.event;

import com.pay.common.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSignupProducer {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
    private final String TOPIC = "user.signup";

    public void send(UserCreatedEvent event) {
        log.info("Kafka 이벤트 전송: {}", event.getEmail());
        kafkaTemplate.send(TOPIC, event);
    }
}
