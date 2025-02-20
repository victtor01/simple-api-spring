package com.delivery.demo.core.application.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.delivery.demo.core.utils.CookieKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.delivery.demo.core.application.interfaces.JwtService;
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
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Autowired
    public SecurityFilter(UsersRepository usersRepository, JwtService jwtService) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String requestPath = request.getServletPath();
//        logger.info("Request Path: " + requestPath);
//
//        List<String> publicPaths = Arrays.asList("/auth/login", "/auth/register", "/public/", "/swagger-ui/", "/swagger-resources/", "/webjars/");
//
//        boolean isPublicPath = publicPaths.stream().anyMatch(requestPath::startsWith);
//
//        return isPublicPath;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null) {
            Claims claims = jwtService.extractAllClaims(token);
            String email = claims.get("email", String.class);
            UserDetails user = usersRepository.findByEmail(email).orElseThrow();

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CookieKeys.ACCESS_TOKEN.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
