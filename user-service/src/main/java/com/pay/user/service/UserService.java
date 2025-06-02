package com.pay.user.service;

import com.pay.common.event.UserDelEvent;
import com.pay.user.domain.UserProfile;
import com.pay.user.dto.UserProfileResponse;
import com.pay.user.event.UserDelProducer;
import com.pay.user.grpc.AuthUserServiceClient;
import com.pay.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthUserServiceClient authUserServiceClient;
    private final UserDelProducer userDelProducer;

    public UserProfileResponse getProfile(String email) {
        UserProfile userProfile = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        return new UserProfileResponse(
                userProfile.getEmail(),
                userProfile.getNickname(),
                userProfile.getJoinedAt()
        );
    }

    @Transactional
    public void deleteUser(String email) {

        userRepository.deleteByEmail(email);

        // ✅ gRPC 호출 실패 시 예외 던져서 트랜잭션 전체 롤백
        try {
            authUserServiceClient.deleteUser(email);
            log.info("User 삭제 확인");
        } catch (Exception e) {
            throw new RuntimeException("User 삭제 실패");
        }

        UserDelEvent event = UserDelEvent.builder()
                .email(email)
                .build();

        userDelProducer.send(event);
    }
}
