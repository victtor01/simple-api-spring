package com.delivery.demo.infra.api.controllers;

import com.delivery.demo.core.application.dtos.WorkspaceDTO;
import com.delivery.demo.core.application.dtos.CreateWorkspaceDTO;
import com.delivery.demo.core.application.interfaces.WorkspaceService;
import com.delivery.demo.core.application.interfaces.AuthenticationUtils;
import com.delivery.demo.core.domain.Workspace;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspacesService;
    private final AuthenticationUtils authenticationUtils;

    private static final Logger logger = Logger.getLogger(WorkspaceController.class.getName());

    @Autowired
    public WorkspaceController(WorkspaceService areasService, AuthenticationUtils authenticationUtilsImplements) {
        this.workspacesService = areasService;
        this.authenticationUtils = authenticationUtilsImplements;
    }

    @PostMapping
    public ResponseEntity<WorkspaceDTO> Create(@RequestBody @Valid CreateWorkspaceDTO createAreaDTO) {
        UUID userId = authenticationUtils.getId();

        Workspace workspace = workspacesService.create(createAreaDTO, userId);
        WorkspaceDTO responseWorkspace = WorkspaceDTO.builder()
            .name(workspace.getName())
            .id(workspace.getId())
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseWorkspace);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> findAllByUser() {
        UUID userId = authenticationUtils.getId();
        List<Workspace> workspace = this.workspacesService.findAll(userId);

        List<WorkspaceDTO> workspacesDTOS = workspace.stream()
            .map(area -> new WorkspaceDTO(area.getId(), area.getName()))
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(workspacesDTOS);
    }
}
