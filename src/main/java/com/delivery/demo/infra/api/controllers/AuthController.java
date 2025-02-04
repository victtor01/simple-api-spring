package com.delivery.demo.infra.api.controllers;

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

import com.delivery.demo.core.application.dtos.AuthDTO;
import com.delivery.demo.core.application.dtos.LoginDTO;
import com.delivery.demo.core.application.interfaces.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping()
    public ResponseEntity<AuthDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        String token = authService.authenticateAndGenerateToken(loginDTO.getEmail(), loginDTO.getPassword());

        ResponseCookie cookie = ResponseCookie.from("__access_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 1)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthDTO(token));
    }

    @GetMapping()
    public ResponseEntity<String> MyInformations() {
        return ResponseEntity.status(HttpStatus.OK).body("example");
    }
}
