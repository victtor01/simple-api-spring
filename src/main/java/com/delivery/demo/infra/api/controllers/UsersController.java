package com.delivery.demo.infra.api.controllers;

import com.delivery.demo.core.application.mappers.UserMapper;
import com.delivery.demo.core.application.mappers.UserMapperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.delivery.demo.core.application.dtos.UserDTO;
import com.delivery.demo.core.application.interfaces.UsersService;
import com.delivery.demo.core.domain.User;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService _usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this._usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<UserMapperDTO> Create(@RequestBody() UserDTO userDto) {
        User user = _usersService.Create(userDto);

        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toMapper(user));
    }
}
