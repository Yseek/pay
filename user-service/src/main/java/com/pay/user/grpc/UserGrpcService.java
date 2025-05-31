package com.pay.user.grpc;

import com.pay.user.domain.UserProfile;
import com.pay.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserGrpcService extends UserProfileServiceGrpc.UserProfileServiceImplBase{

    private final UserRepository userRepository;

    @Override
    public void createProfile(CreateProfileRequest request, StreamObserver<CreateProfileResponse> responseObserver) {

        log.info("gRPC 회원가입 수신: {}", request.getEmail());

        UserProfile profile = UserProfile.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .joinedAt(LocalDateTime.now())
                .build();

        UserProfile saved = userRepository.save(profile);

        log.info("gRPC 회원가입 UserProfile: {}", saved);

        CreateProfileResponse response = CreateProfileResponse.newBuilder()
                .setUserProfileId(saved.getId().toString())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
