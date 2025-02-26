package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.dtos.CreateWorkspaceDTO;
import com.delivery.demo.core.application.interfaces.WorkspaceService;
import com.delivery.demo.core.domain.User;
import com.delivery.demo.core.domain.Workspace;
import com.delivery.demo.infra.config.errors.NotFoundException;
import com.delivery.demo.infra.config.errors.UnauthorizedException;
import com.delivery.demo.infra.repositories.WorkspacesRepository;
import com.delivery.demo.infra.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WorkspacesImplements implements WorkspaceService {
    private final WorkspacesRepository workspacesRepository;
    private final UsersRepository usersRepository;

    public WorkspacesImplements(WorkspacesRepository areasRepository, UsersRepository usersRepository) {
        this.workspacesRepository = areasRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Workspace create(CreateWorkspaceDTO createWorkspaceDTO, UUID userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("user not found to create a new area!");
        });

        String name = createWorkspaceDTO.getName();

        workspacesRepository.findOneByName(name).ifPresent(area -> {
            throw new UnauthorizedException("Área já existe no banco de dados.");
        });

        Workspace area = Workspace.builder().items(new ArrayList<>()).name(name).user(user).build();
        Workspace created = this.workspacesRepository.save(area);

        return created;
    }

    @Override
    public List<Workspace> findAll(UUID userId) {
        return this.workspacesRepository.findAllByUserId(userId);
    }
}
