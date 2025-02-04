package com.delivery.demo.core.application.dtos;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(@NotNull String token) { }
