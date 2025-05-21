package com.pay.auth.controller;

import com.pay.auth.domain.User;
import com.pay.auth.service.UserService;
import com.pay.common.dto.SignupRequest;
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
}
