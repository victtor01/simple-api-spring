package com.delivery.demo.core.application.interfaces;

import com.delivery.demo.core.application.dtos.AreaDTO;
import com.delivery.demo.core.application.dtos.CreateAreaDTO;
import com.delivery.demo.core.domain.Area;

import java.util.List;
import java.util.UUID;

public interface AreasService {
    Area create(CreateAreaDTO areaDTO, UUID userId);
    List<Area> findAll(UUID userId);
}
