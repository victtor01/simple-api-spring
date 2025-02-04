package com.delivery.demo.core.application.interfaces;

import io.jsonwebtoken.Claims;

public interface ClaimsResolver<T> {
    T resolve(Claims claims);
}
