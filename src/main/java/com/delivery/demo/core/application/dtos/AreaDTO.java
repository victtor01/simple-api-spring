package com.delivery.demo.core.application.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {
    private UUID id;
    private String name;
}
