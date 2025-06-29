package com.pay.auth.service;

import com.pay.auth.domain.User;
import com.pay.auth.dto.LoginRequest;
import com.pay.auth.dto.SignupRequest;
import com.pay.auth.dto.TokenResponse;
import com.pay.auth.event.UserSignupProducer;
import com.pay.auth.grpc.UserProfileGrpcClient;
import com.pay.auth.repositroy.UserRepository;
import com.pay.common.event.UserCreatedEvent;
import com.pay.common.jwt.JwtProvider;
import com.pay.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserSignupProducer userSignupProducer;
    private final UserProfileGrpcClient userProfileGrpcClient;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Transactional
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

        user = userRepository.save(user);

        // ✅ gRPC 호출 실패 시 예외 던져서 트랜잭션 전체 롤백
        try {
            String profile = userProfileGrpcClient.createProfile(user.getEmail(), user.getNickname());
            log.info("profile gRPC 리턴 확인 : {}", profile);
        } catch (Exception e) {
            throw new RuntimeException("UserProfile 생성 실패");
        }

        UserCreatedEvent event = UserCreatedEvent.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        userSignupProducer.send(event);

        return user;
    }

    public TokenResponse login(LoginRequest request) {
        String email = request.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createToken(user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail());

        refreshTokenService.save(email, refreshToken);

        return new TokenResponse(
                accessToken,
                refreshToken
        );
    }

    public TokenResponse reissue(String refreshToken) {
        if (!jwtUtil.validate(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 refresh 토큰입니다.");
        }

        String email = jwtUtil.extractEmail(refreshToken);
        log.info("refresh Email : {}", email);

        String savedRefreshToken = refreshTokenService.get(email);
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("일치하지 않는 refresh 토큰이빈다.");
        }

        String newAccessToken = jwtProvider.createToken(email);
        String newRefreshToken = jwtProvider.createRefreshToken(email);

        refreshTokenService.save(email, newRefreshToken);

        return new TokenResponse(
                newAccessToken,
                newRefreshToken
        );
    }
}
