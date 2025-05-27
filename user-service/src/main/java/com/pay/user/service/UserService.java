package com.pay.user.service;

import com.pay.user.domain.UserProfile;
import com.pay.user.dto.UserProfileResponse;
import com.pay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getProfile(String email) {
        UserProfile userProfile = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        return new UserProfileResponse(
                userProfile.getEmail(),
                userProfile.getNickname(),
                userProfile.getJoinedAt()
        );
    }
}
