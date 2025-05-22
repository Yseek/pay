package com.pay.auth.controller;

import com.pay.auth.domain.User;
import com.pay.auth.security.UserPrincipal;
import com.pay.auth.service.UserService;
import com.pay.common.dto.LoginRequest;
import com.pay.common.dto.SignupRequest;
import com.pay.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<String>> me(Authentication authentication) {
        String email = ((UserPrincipal) authentication.getPrincipal()).getEmail();
        return ResponseEntity.ok(ApiResponse.success("로그인된 사용자: " + email));
    }
}
