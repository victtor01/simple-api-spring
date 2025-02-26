package com.delivery.demo.core.application.interfaces;

import org.springframework.http.ResponseCookie;

public interface CookiesService {
    ResponseCookie createTokenCookie(String token);
    ResponseCookie createRefreshTokenCookie(String refreshToken);
}
