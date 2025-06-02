package com.pay.user.grpc;

import com.pay.auth.grpc.AuthUserServiceGrpc;
import com.pay.auth.grpc.DeleteUserRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class AuthUserServiceClient {

    @GrpcClient("auth-service")
    private AuthUserServiceGrpc.AuthUserServiceBlockingStub authStub;

    public void deleteUser(String email) {
        DeleteUserRequest request = DeleteUserRequest.newBuilder()
                .setEmail(email)
                .build();

        authStub.deleteUser(request);
    }
}
