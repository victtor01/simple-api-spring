package com.delivery.demo.core.application.services;

import com.delivery.demo.core.application.dtos.AreaDTO;
import com.delivery.demo.core.application.dtos.CreateAreaDTO;
import com.delivery.demo.core.application.interfaces.AreasService;
import com.delivery.demo.core.domain.Area;
import com.delivery.demo.core.domain.User;
import com.delivery.demo.infra.config.errors.NotFoundException;
import com.delivery.demo.infra.repositories.AreasRepository;
import com.delivery.demo.infra.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AreasServiceImplements implements AreasService {
    private final AreasRepository areasRepository;
    private final UsersRepository usersRepository;

    public AreasServiceImplements(AreasRepository areasRepository, UsersRepository usersRepository) {
        this.areasRepository = areasRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public Area create(CreateAreaDTO createAreaDTO, UUID userId) {
        try {
            String name = createAreaDTO.getName();

            var user = usersRepository.findById(userId).orElseThrow(() -> {
                throw new NotFoundException("user not found to create a new area!");
            });

            Area area = Area.builder()
                    .items(new ArrayList<>())
                    .name(name).user(user).build();

            Area created = this.areasRepository.save(area);

            return created;
        } catch (Exception e) {
            throw new RuntimeException("error in create a new area!");
        }
    }

    @Override
    @Transactional
    public List<Area> findAll(UUID userId) {
        try {
            List<Area> areas = this.areasRepository.findAllByUserId(userId);
            return areas;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
