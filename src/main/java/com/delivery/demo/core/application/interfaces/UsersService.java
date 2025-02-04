package com.delivery.demo.core.application.interfaces;

import com.delivery.demo.core.application.dtos.UserDTO;
import com.delivery.demo.core.domain.User;

public interface UsersService {
    User Create(UserDTO userDto);
}
