package com.example.jwt.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.jwt.dto.response.UserResponse;
import com.example.jwt.entities.UserEntity;

@Component
public class UserMapper {

    public UserResponse toResponse(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setAddress(entity.getAddress());
        response.setPhone(entity.getPhone());
        response.setRole(entity.getRole());

        return response;
    }
}