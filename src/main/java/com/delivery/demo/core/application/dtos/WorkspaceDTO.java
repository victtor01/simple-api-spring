package com.delivery.demo.core.application.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDTO {
    private UUID id;
    private String name;
}
