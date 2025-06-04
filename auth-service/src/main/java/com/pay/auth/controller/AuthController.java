package com.pay.auth.controller;

import com.pay.auth.domain.User;
import com.pay.auth.dto.TokenReissueRequest;
import com.pay.auth.dto.TokenResponse;
import com.pay.auth.service.UserService;
import com.pay.auth.dto.LoginRequest;
import com.pay.auth.dto.SignupRequest;
import com.pay.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody SignupRequest request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse token = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@Valid @RequestBody TokenReissueRequest request) {
        TokenResponse token = userService.reissue(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.success(token));
    }
}
