package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImplements implements JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.long-expiration-time}")
    private long jwtLongExpiration;

    @Override
    public String generateToken(Map<String, String> extraClaims, String subject) {
        return generateToken(extraClaims, subject, this.jwtExpiration);
    }

    @Override
    public String generateLongToken(Map<String, String> extraClaims, String subject) {
        return generateToken(extraClaims, subject, this.jwtLongExpiration);
    }

    @Override
    public String generateToken(Map<String, String> extraClaims, String subject, long expireInterval) {
        return Jwts.builder()
            .claims().add(extraClaims).and()
            .subject(subject)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expireInterval))
            .signWith(getSignInKey())
            .compact();
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired: " + e.getMessage());
            return true;
        } catch (Exception e) {
            // Outros erros (token inválido, malformado, etc.)
            System.out.println("Error while checking token expiration: " + e.getMessage());
            return true; // Considera o token como expirado em caso de erro
        }
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        Claims claims = extractAllClaims(refreshToken);
        String email = claims.get("email", String.class);

        return generateToken(Map.of("email", email), email);
    }


    private SecretKey getSignInKey() {
        byte[] keyBytes = this.secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}