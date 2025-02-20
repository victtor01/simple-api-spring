package com.delivery.demo.infra.config.errors;

import java.util.Map;

public class UnauthorizedException extends ErrorInstance {
    final static int status = 404;


    public UnauthorizedException(String message) {
        this(message, Map.of());
    }

    public UnauthorizedException(String message, Map<String, String[]> errors) {
        super(message, status);
        setType("Unauthorized");
        setErrors(errors != null ? errors : Map.of());
    }
}
