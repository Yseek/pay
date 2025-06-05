package com.pay.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "공통 응답 모델")
public class CommonResponse<T> {
    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    public CommonResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, "success", data);
    }

    public static <T> CommonResponse<T> error(String message) {
        return new CommonResponse<>(false, message, null);
    }
}