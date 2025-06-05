package com.pay.point.controller;

import com.pay.common.response.CommonResponse;
import com.pay.point.dto.PointWalletResponse;
import com.pay.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/wallet")
    public ResponseEntity<CommonResponse<PointWalletResponse>> getPointWallet(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CommonResponse.error("인증되지 않은 요청입니다."));
        }

        PointWalletResponse wallet = pointService.getPointWallet(email);
        return ResponseEntity.ok(CommonResponse.success(wallet));
    }
}
