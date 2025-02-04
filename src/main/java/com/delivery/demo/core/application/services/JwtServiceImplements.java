package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.interfaces.JwtService;
import io.jsonwebtoken.Claims;
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

    @Override
    public String generateToken(Map<String, String> extraClaims, String email) {
        return Jwts
                .builder()
                .claims().add(extraClaims)
                .and()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String generateToken(Map<String, String> extraClaims, String userName, long expireInterval) {
        return Jwts
                .builder()
                .claims().add(extraClaims)
                .and()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireInterval))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private SecretKey getSignInKey() {
        byte[] keyBytes = this.secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}