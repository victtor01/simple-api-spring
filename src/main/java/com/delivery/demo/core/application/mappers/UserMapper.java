package com.delivery.demo.core.application.mappers;

import com.delivery.demo.core.domain.User;

public class UserMapper {
    public static UserMapperDTO toMapper(User user) {
        UserMapperDTO userMapperDTO = new UserMapperDTO(user.getEmail(), user.getName());
        return userMapperDTO;
    }
}

