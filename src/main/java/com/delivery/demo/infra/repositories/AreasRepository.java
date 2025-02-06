package com.delivery.demo.infra.repositories;

import com.delivery.demo.core.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AreasRepository extends JpaRepository<Area, UUID> {
    List<Area> findAllByUserId(UUID userId);
}
