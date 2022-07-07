package com.example.letsgongbu.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final int status;
    private final String errorName;
    private final String errorCode;
    private final String errorMsg;

    public static ResponseEntity<ErrorResponse> toResponseEntity(Error error) {
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(error.getHttpStatus().value())
                        .errorName(error.getHttpStatus().name())
                        .errorCode(error.getErrorCode())
                        .errorMsg(error.getErrorMessage())
                        .build()
                );
    }
}
