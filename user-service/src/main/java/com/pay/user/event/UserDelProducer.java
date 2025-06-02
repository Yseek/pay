package com.pay.user.event;

import com.pay.common.event.UserDelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDelProducer {

    private final KafkaTemplate<String, UserDelEvent> kafkaTemplate;
    private final String TOPIC = "user.del";

    public void send(UserDelEvent event) {
        log.info("Kafka 이벤트 전송: {}", event.getEmail());
        kafkaTemplate.send(TOPIC, event);
    }
}
