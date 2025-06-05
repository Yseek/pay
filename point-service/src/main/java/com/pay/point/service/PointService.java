package com.pay.point.service;

import com.pay.common.event.PaymentEvent;
import com.pay.point.domain.PointWallet;
import com.pay.point.dto.PointWalletResponse;
import com.pay.point.repository.PointWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointWalletRepository pointWalletRepository;

    public PointWalletResponse getPointWallet(String email) {
        PointWallet wallet = pointWalletRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("지갑 정보가 없습니다."));

        return new PointWalletResponse(
                wallet.getBalance()
        );
    }

    public void decreasePoint(PaymentEvent event) {
        PointWallet wallet = pointWalletRepository.findByEmail(event.getEmail())
                .orElseThrow(() -> new RuntimeException("지갑이 없습니다."));

        if (wallet.getBalance() < event.getAmount()) {
            throw new RuntimeException("포인트 부족");
        }

        wallet.setBalance(wallet.getBalance() - event.getAmount());
        pointWalletRepository.save(wallet);
    }
}
