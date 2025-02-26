package com.delivery.demo.infra.repositories;

import com.delivery.demo.core.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspacesRepository extends JpaRepository<Workspace, UUID> {
    List<Workspace> findAllByUserId(UUID userId);
    Optional<Workspace> findOneByName(String name);
}
