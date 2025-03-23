package com.example.jwt.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.user.GetUserInfoUsecase;
import com.example.jwt.application.usecase.user.UpdateUserInfoUsecase;
import com.example.jwt.dto.mapper.UserMapper;
import com.example.jwt.dto.request.UpdateUserRequest;
import com.example.jwt.dto.response.UserResponse;
import com.example.jwt.entities.UserEntity;
import com.example.jwt.infra.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements GetUserInfoUsecase, UpdateUserInfoUsecase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse execute(String userId) {
        UserEntity user = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return userMapper.toResponse(user);
    }

    @Override
    public void execute(String userId, UpdateUserRequest updateUserRequest) {
        UserEntity user = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getAddress() != null) {
            user.setAddress(updateUserRequest.getAddress());
        }
        if (updateUserRequest.getPhone() != null) {
            user.setPhone(updateUserRequest.getPhone());
        }

        userRepository.save(user);
    }
}