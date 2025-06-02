package com.pay.auth.grpc;

import com.pay.auth.repositroy.UserRepository;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AuthUserSevice extends AuthUserServiceGrpc.AuthUserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteUser(DeleteUserRequest request, StreamObserver<DeleteUserResponse> responseObserver) {
        userRepository.deleteByEmail(request.getEmail());
        responseObserver.onNext(DeleteUserResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
