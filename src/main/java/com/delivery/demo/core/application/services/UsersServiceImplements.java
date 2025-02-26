package com.delivery.demo.core.application.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.delivery.demo.core.application.dtos.UserDTO;
import com.delivery.demo.core.application.interfaces.UsersService;
import com.delivery.demo.core.domain.User;
import com.delivery.demo.infra.config.errors.NotFoundException;
import com.delivery.demo.infra.repositories.UsersRepository;

@Service
public class UsersServiceImplements implements UsersService {

    private final UsersRepository _usersRepository;

    public UsersServiceImplements(UsersRepository usersRepository) {
        _usersRepository = usersRepository;
    }

    @Override
    public User Create(UserDTO userDto) {
        String email = userDto.getEmail();

        var userExists = _usersRepository.findByEmail(email);

        if (userExists.isPresent()) {
            throw new NotFoundException("user exists");
        }

        BCryptPasswordEncoder encryptPassword = new BCryptPasswordEncoder();
        String newPassword = encryptPassword.encode(userDto.getPassword());

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(email);
        user.setPassword(newPassword);

        return this._usersRepository.save(user);
    }

}




