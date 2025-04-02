package com.example.crud_demo.mapper;

import com.example.crud_demo.dto.UserSeedDTO;
import com.example.crud_demo.model.User;

public class UserMapper {
    public static User toEntity(UserSeedDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
