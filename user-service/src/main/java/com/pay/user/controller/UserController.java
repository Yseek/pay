package com.pay.user.controller;

import com.pay.user.domain.UserProfile;
import com.pay.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        String email = (String) request.getAttribute("userEmail");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("옳지 않은 접근 입니다.");
        }

        UserProfile profile = userService.getProfile(email);
        return ResponseEntity.ok(profile);
    }
}
