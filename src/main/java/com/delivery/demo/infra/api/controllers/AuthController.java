package com.delivery.demo.infra.api.controllers;

import com.delivery.demo.core.application.interfaces.CookiesService;
import com.delivery.demo.core.application.records.JwtTokensDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.demo.core.application.dtos.LoginDTO;
import com.delivery.demo.core.application.interfaces.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CookiesService cookiesService;

    @PostMapping()
    public ResponseEntity<JwtTokensDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        JwtTokensDTO tokens = authService.authenticateAndGenerateTokens(loginDTO.getEmail(), loginDTO.getPassword());

        ResponseCookie accessTokenCookie = cookiesService.createTokenCookie(tokens.accessToken());
        ResponseCookie refreshTokenCookie = cookiesService.createRefreshTokenCookie(tokens.refreshToken());

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())  // Adiciona o refreshTokenCookie
            .body(tokens);
    }

    @GetMapping()
    public ResponseEntity<String> MyInformations() {
        return ResponseEntity.status(HttpStatus.OK).body("THIS IS EXAMPLE");
    }
}
