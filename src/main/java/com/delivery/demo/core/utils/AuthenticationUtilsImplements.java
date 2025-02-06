package com.delivery.demo.core.utils;

import com.delivery.demo.core.application.interfaces.AuthenticationUtils;
import com.delivery.demo.core.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticationUtilsImplements implements AuthenticationUtils {

    @Override
    public UUID getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
