package com.pay.auth.service;

import com.pay.auth.domain.User;
import com.pay.auth.event.UserSignupProducer;
import com.pay.auth.repositroy.UserRepository;
import com.pay.auth.util.JwtProvider;
import com.pay.auth.dto.LoginRequest;
import com.pay.auth.dto.SignupRequest;
import com.pay.common.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserSignupProducer userSignupProducer;

    public User signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        UserCreatedEvent event = UserCreatedEvent.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        userSignupProducer.send(event);

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(user.getEmail());
    }
}
