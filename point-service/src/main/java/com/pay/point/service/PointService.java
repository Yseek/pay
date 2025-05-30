package com.pay.point.service;

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
}
