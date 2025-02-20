package com.delivery.demo.infra.config.errors;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorInstance extends RuntimeException {
    private final int statusCode;
    private String type = "Internal Server Error"; // Tipo de erro padrão
    private Map<String, String[]> errors;

    public ErrorInstance(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
