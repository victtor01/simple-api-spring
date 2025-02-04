package com.delivery.demo.infra.config.errors;

import java.util.Map;

public class NotFoundException extends ErrorInstance {
    private static final int statusCode = 404;

    public NotFoundException(String message, Map<String, String[]> errors) {
        super(message, statusCode);
        setType("Not Found");
        setErrors(errors != null ? errors : Map.of());
    }

    public NotFoundException(String message) {
        super(message, statusCode);
        setType("Not Found");
        setErrors(Map.of());
    }
}
