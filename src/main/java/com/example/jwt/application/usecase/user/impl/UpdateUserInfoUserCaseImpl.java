package com.example.jwt.application.usecase.user.impl;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.user.UpdateUserInfoUseCase;
import com.example.jwt.dto.request.UpdateUserRequest;
import com.example.jwt.entities.UserEntity;
import com.example.jwt.infra.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UpdateUserInfoUserCaseImpl implements UpdateUserInfoUseCase {
    private final UserRepository userRepository;

    @Override
    public void execute(int userId, UpdateUserRequest updateUserRequest) {
        try {
            UserEntity user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User Not Found"));

            user.setEmail(updateUserRequest.getEmail());
            user.setAddress(updateUserRequest.getAddress());
            user.setPhone(updateUserRequest.getPhone());

            this.userRepository.save(user);
        } catch (RuntimeException e) {
            System.out.println("UpdateUserInfoUseCase execute exception: " + e.getMessage());
        }
    }
}
