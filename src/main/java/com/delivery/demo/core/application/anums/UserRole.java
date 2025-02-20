package com.delivery.demo.core.application.anums;

import lombok.Getter;

public enum UserRole {
    USER("ROLE_USER");

    @Getter
    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
