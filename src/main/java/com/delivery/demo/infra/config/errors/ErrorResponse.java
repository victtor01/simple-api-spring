package com.delivery.demo.infra.config.errors;

public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private final int statusCode;

    public ErrorResponse(String errorCode, String errorMessage, int statusCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    // Getters e Setters
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
