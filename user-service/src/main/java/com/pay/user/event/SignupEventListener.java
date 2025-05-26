package com.pay.user.event;

import com.pay.common.event.UserCreatedEvent;
import com.pay.user.domain.UserProfile;
import com.pay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignupEventListener {

    private final UserRepository userRepository;

    @KafkaListener(topics = "user.signup", groupId = "user-service")
    public void handle(UserCreatedEvent event) {
        log.info("Kafka 회원가입 이벤트 수신: {}", event.getEmail());

        if (userRepository.existsByEmail(event.getEmail())){
            log.warn("이미 존재하는 사용자, 무시: {}", event.getEmail());
            return;
        }

        userRepository.save(UserProfile.builder().
                email(event.getEmail())
                .nickname(event.getNickname())
                .joinedAt(LocalDateTime.now())
                .build());
    }
}
