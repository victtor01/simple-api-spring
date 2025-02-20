package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.interfaces.AuthService;
import com.delivery.demo.core.application.interfaces.JwtService;
import com.delivery.demo.infra.config.errors.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AuthServiceImplements implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImplements(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private static final Logger logger = Logger.getLogger(AuthServiceImplements.class.getName());

    @Override
    public String authenticateAndGenerateToken(String email, String password) {
        var emailAndPassword = new UsernamePasswordAuthenticationToken(email, password);

        try {
            logger.info(email);
            logger.info(password);
            var auth = authenticationManager.authenticate(emailAndPassword);

            Map<String, String> extraClaims = new HashMap<>();

            extraClaims.put("role", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
            extraClaims.put("email", auth.getName());

            logger.info("authenticated user!");
            return jwtService.generateToken(extraClaims, auth.getName());
        } catch (Exception e) {
            throw new UnauthorizedException("email ou senha incorretos");
        }

    }
}
