package com.delivery.demo.infra.api.controllers;

import com.delivery.demo.core.application.dtos.AreaDTO;
import com.delivery.demo.core.application.dtos.CreateAreaDTO;
import com.delivery.demo.core.application.interfaces.AreasService;
import com.delivery.demo.core.application.interfaces.AuthenticationUtils;
import com.delivery.demo.core.domain.Area;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/areas")
public class AreasController {

    private final AreasService areasService;
    private final AuthenticationUtils authenticationUtils;

    private static final Logger logger = Logger.getLogger(AreasController.class.getName());

    public AreasController(AreasService areasService, AuthenticationUtils authenticationUtilsImplements) {
        this.areasService = areasService;
        this.authenticationUtils = authenticationUtilsImplements;
    }


    @PostMapping
    public ResponseEntity<AreaDTO> Create(@RequestBody CreateAreaDTO createAreaDTO) {
        UUID userId = authenticationUtils.getId();

        Area area = areasService.create(createAreaDTO, userId);
        AreaDTO responseArea = AreaDTO.builder()
                .name(area.getName())
                .id(area.getId())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseArea);
    }

    @GetMapping
    public ResponseEntity<List<AreaDTO>> findAllByUser() {
        UUID userId = authenticationUtils.getId();
        List<Area> areas = this.areasService.findAll(userId);

        List<AreaDTO> areaDTOS = areas.stream()
                .map(area -> new AreaDTO(area.getId(), area.getName()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(areaDTOS);
    }
}
