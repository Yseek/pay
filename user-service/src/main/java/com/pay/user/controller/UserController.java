package com.pay.user.controller;

import com.pay.common.response.CommonResponse;
import com.pay.user.dto.UserProfileResponse;
import com.pay.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "현재 사용자 정보 조회",
            description = "인증된 사용자의 프로필 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 사용자 정보를 가져옴"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "접근 권한 없음")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserProfileResponse>> me(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        if (email == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(CommonResponse.error("옳지 않은 접근입니다."));
        }

        UserProfileResponse profile = userService.getProfile(email);
        return ResponseEntity.ok(CommonResponse.success(profile));
    }

    @Operation(summary = "계정 삭제", description = "인증된 사용자의 계정을 삭제합니다.")
    @DeleteMapping("/me")
    public ResponseEntity<CommonResponse<Void>> deleteMe(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        userService.deleteUser(email);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

}
