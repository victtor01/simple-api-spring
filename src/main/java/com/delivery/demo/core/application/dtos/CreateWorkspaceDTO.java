package com.delivery.demo.core.application.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateWorkspaceDTO {
    private String name;
}
