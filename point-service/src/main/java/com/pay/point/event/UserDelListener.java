package com.pay.point.event;

import com.pay.common.event.UserDelEvent;
import com.pay.point.repository.PointWalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDelListener {

    private final PointWalletRepository pointWalletRepository;

    @KafkaListener(topics = "user.del", groupId = "point-service")
    @Transactional
    public void handleUserDel(UserDelEvent event) {
        log.info("✅ 유저 삭제 이벤트 수신: {}", event.getEmail());

        pointWalletRepository.deleteByEmail(event.getEmail());

        log.info("✅ 유저 지갑 삭제 완료");
    }
}
