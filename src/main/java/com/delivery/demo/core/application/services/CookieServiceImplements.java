package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.interfaces.CookiesService;
import com.delivery.demo.core.utils.CookieKeys;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImplements implements CookiesService {

    @Override
    public ResponseCookie createTokenCookie(String token) {
        long cookieMaxAgeMinutes = 60 * 60;
        return ResponseCookie.from(CookieKeys.ACCESS_TOKEN, token)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(cookieMaxAgeMinutes)
            .build();
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        long cookieLongMaxAgeMinutes = 60 * 60;
        return ResponseCookie.from(CookieKeys.REFRESH_TOKEN, refreshToken)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(cookieLongMaxAgeMinutes)
            .build();
    }
}
