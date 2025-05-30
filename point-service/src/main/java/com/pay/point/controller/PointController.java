package com.pay.point.controller;

import com.pay.common.response.ApiResponse;
import com.pay.point.dto.PointWalletResponse;
import com.pay.point.service.PointService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/wallet")
    public ResponseEntity<ApiResponse<PointWalletResponse>> getPointWallet(HttpServletRequest request) {
        String email = (String) request.getAttribute("userEmail");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("인증되지 않은 요청입니다."));
        }

        PointWalletResponse wallet = pointService.getPointWallet(email);
        return ResponseEntity.ok(ApiResponse.success(wallet));
    }
}
