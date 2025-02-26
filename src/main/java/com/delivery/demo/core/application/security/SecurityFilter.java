package com.delivery.demo.core.application.security;

import java.io.IOException;
import java.util.logging.Logger;

import com.delivery.demo.core.application.interfaces.CookiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.delivery.demo.core.application.interfaces.JwtService;
import com.delivery.demo.core.utils.CookieKeys;
import com.delivery.demo.infra.repositories.UsersRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsersRepository usersRepository;
    private final CookiesService cookiesService;

    private static final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Autowired
    public SecurityFilter(UsersRepository usersRepository, JwtService jwtService, CookiesService cookiesService) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
        this.cookiesService = cookiesService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = recoverToken(request, CookieKeys.ACCESS_TOKEN);
        String refreshToken = recoverToken(request, CookieKeys.REFRESH_TOKEN);

        logger.info("Access Token: " + accessToken);
        logger.info("Refresh Token: " + refreshToken);

        if (accessToken == null && refreshToken == null) {
            logger.info("entrou no NULL");
            filterChain.doFilter(request, response);
            return;
        }

        var isRefreshExpired = jwtService.isTokenExpired(refreshToken);
        var isAccessTokenExpired = jwtService.isTokenExpired(accessToken);

        if (isAccessTokenExpired && isRefreshExpired) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isAccessTokenExpired) {
            accessToken = refreshAccessToken(refreshToken, response);
        }

        Claims claims = jwtService.extractAllClaims(accessToken);
        String email = claims.get("email", String.class);
        UserDetails user = usersRepository.findByEmail(email).orElseThrow();

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String refreshAccessToken(String refreshToken, HttpServletResponse response) {
        logger.info("Refreshing access token...");
        String newAccessToken = jwtService.refreshAccessToken(refreshToken);
        ResponseCookie newAccessTokenCookie = cookiesService.createTokenCookie(newAccessToken);
        response.addHeader(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString());

        return newAccessToken;
    }

    private String recoverToken(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
