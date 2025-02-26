package com.delivery.demo.core.application.interfaces;

import com.delivery.demo.core.application.dtos.CreateWorkspaceDTO;
import com.delivery.demo.core.domain.Workspace;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {
    Workspace create(CreateWorkspaceDTO createWorkspaceDTO, UUID userId);
    List<Workspace> findAll(UUID userId);
}
