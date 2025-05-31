package com.pay.auth.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class UserProfileGrpcClient {

    @GrpcClient("user-profile")
    private UserProfileServiceGrpc.UserProfileServiceBlockingStub userProfileStub;

    public String createProfile(String email, String nickname) {
        CreateProfileRequest request = CreateProfileRequest.newBuilder()
                .setEmail(email)
                .setNickname(nickname)
                .build();

        CreateProfileResponse response = userProfileStub.createProfile(request);
        return response.getUserProfileId();
    }
}
