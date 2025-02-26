package com.delivery.demo.core.application.services;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.delivery.demo.core.application.records.JwtTokensDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.delivery.demo.core.application.interfaces.AuthService;
import com.delivery.demo.core.application.interfaces.JwtService;
import com.delivery.demo.infra.config.errors.UnauthorizedException;

@Service
public class AuthServiceImplements implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final Logger logger = Logger.getLogger(AuthServiceImplements.class.getName());

    @Autowired
    public AuthServiceImplements(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public JwtTokensDTO authenticateAndGenerateTokens(String email, String password) {
        var emailAndPassword = new UsernamePasswordAuthenticationToken(email, password);

        try {
            var auth = authenticationManager.authenticate(emailAndPassword);

            Map<String, String> extraClaims = new HashMap<>();

            extraClaims.put("role", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));

            extraClaims.put("email", auth.getName());

            String accessToken = jwtService.generateToken(extraClaims, auth.getName());
            String refreshToken = jwtService.generateLongToken(extraClaims, auth.getName());

            var tokens = new JwtTokensDTO(accessToken, refreshToken);

            return tokens;
        } catch (Error e) {
            logger.info(e.toString());
            throw new UnauthorizedException("email ou senha incorretos");
        }
    }
}
