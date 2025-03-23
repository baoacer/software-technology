package com.example.jwt.application.usecase.user.impl;

import com.example.jwt.application.exceptions.EmailAlreadyExistsException;
import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.user.UpdateUserInfoUseCase;
import com.example.jwt.dto.model.UserDto;
import com.example.jwt.dto.request.UpdateUserRequest;
import com.example.jwt.entities.UserEntity;
import com.example.jwt.infra.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UpdateUserInfoUserCaseImpl implements UpdateUserInfoUseCase {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateUserInfoUserCaseImpl.class);

    @Override
    public UserDto execute(int userId, UpdateUserRequest updateUserRequest) {
        try {
            UserEntity user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User Not Found"));
            UserEntity existsEmail = this.userRepository.findByEmail(updateUserRequest.getEmail());
            if(existsEmail != null){
                throw new EmailAlreadyExistsException("Email Already Exists");
            }

            user.setEmail(updateUserRequest.getEmail());
            user.setAddress(updateUserRequest.getAddress());
            user.setPhone(updateUserRequest.getPhone());

            UserEntity updateUser = this.userRepository.save(user);

            return UserDto.builder()
                    .id(updateUser.getId())
                    .email(updateUser.getEmail())
                    .address(updateUser.getAddress())
                    .phone(updateUser.getPhone())
                    .createdAt(updateUser.getCreatedAt())
                    .updatedAt(updateUser.getUpdatedAt())
                    .build();
        } catch (RuntimeException e) {
            this.logger.error("Error Update User Info {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
