package com.delivery.demo.infra.config.errors;

import java.util.Map;

public class ErrorInstance extends RuntimeException {
    private final int statusCode;
    private String type = "Internal Server Error"; // Tipo de erro padrão
    private Map<String, String[]> errors;

    public ErrorInstance(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String[]> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String[]> errors) {
        this.errors = errors;
    }
}
