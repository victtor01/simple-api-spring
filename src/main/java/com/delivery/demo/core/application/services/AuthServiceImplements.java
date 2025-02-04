package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.interfaces.AuthService;
import com.delivery.demo.core.application.interfaces.JwtService;
import com.delivery.demo.infra.config.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthServiceImplements implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = Logger.getLogger(AuthServiceImplements.class.getName());

    @Override
    public String authenticateAndGenerateToken(String email, String password) {
        logger.info("PASSANDO NO AUTH");
        var emailAndPassword = new UsernamePasswordAuthenticationToken(email, password);
        var auth = authenticationManager.authenticate(emailAndPassword);

        if (!auth.isAuthenticated()) throw new NotFoundException("email ou senha incorretos!");

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("role", "admin");
        extraClaims.put("email", auth.getName());

        return jwtService.generateToken(extraClaims, auth.getName());

    }
}
