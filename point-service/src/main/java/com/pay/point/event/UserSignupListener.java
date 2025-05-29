package com.pay.point.event;

import com.pay.common.event.UserCreatedEvent;
import com.pay.point.domain.PointWallet;
import com.pay.point.repository.PointWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSignupListener {

    private final PointWalletRepository pointWalletRepository;

    @KafkaListener(topics = "user.signup", groupId = "point-service")
    public void handleUserSignup(UserCreatedEvent event) {
        log.info("✅ 유저 가입 이벤트 수신: {}", event.getEmail());

        pointWalletRepository.save(PointWallet.builder()
                .email(event.getEmail())
                .balance(1000)
                .build());

        log.info("✅ 기본 포인트 1000 지급 완료");
    }
}
