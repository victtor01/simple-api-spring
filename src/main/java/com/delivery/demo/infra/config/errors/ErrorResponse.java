package com.delivery.demo.infra.config.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private final int statusCode;

    public ErrorResponse(String errorCode, String errorMessage, int statusCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }
}
