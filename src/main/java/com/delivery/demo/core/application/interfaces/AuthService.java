package com.delivery.demo.core.application.interfaces;

import com.delivery.demo.core.application.records.JwtTokensDTO;

public interface AuthService {
    JwtTokensDTO authenticateAndGenerateTokens(String email, String password);
}
