package com.delivery.demo.core.application.interfaces;

public interface AuthService {
    String authenticateAndGenerateToken(String email, String password);
}
