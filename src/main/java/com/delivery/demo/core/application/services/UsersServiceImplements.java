package com.delivery.demo.core.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.delivery.demo.core.application.dtos.UserDTO;
import com.delivery.demo.infra.repositories.UsersRepository;
import com.delivery.demo.core.application.interfaces.UsersService;
import com.delivery.demo.core.domain.User;
import com.delivery.demo.infra.config.errors.NotFoundException;

@Service
public class UsersServiceImplements implements UsersService {

    private final UsersRepository _usersRepository;

    @Autowired
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

        BCryptPasswordEncoder encyptPassword = new BCryptPasswordEncoder();
        var newPassword = encyptPassword.encode(userDto.getPassword());

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(email);
        user.setPassword(newPassword);

        return this._usersRepository.save(user);
    }

}




