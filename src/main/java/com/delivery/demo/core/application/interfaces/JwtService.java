package com.delivery.demo.core.application.interfaces;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {
    boolean isTokenExpired(String token);
    String generateToken(Map<String, String> extraClaims, String userName, long expireInterval);
    String generateToken(Map<String, String> extraClaims, String userName);
    Claims extractAllClaims(String token);
}

