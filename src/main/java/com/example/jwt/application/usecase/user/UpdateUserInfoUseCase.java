package com.example.jwt.application.usecase.user;

import com.example.jwt.dto.model.UserDto;
import com.example.jwt.dto.request.UpdateUserRequest;

public interface UpdateUserInfoUseCase {
    UserDto execute(int userId, UpdateUserRequest updateUserRequest);
}
