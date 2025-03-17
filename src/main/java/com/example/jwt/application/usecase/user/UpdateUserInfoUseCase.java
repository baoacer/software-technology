package com.example.jwt.application.usecase.user;

import com.example.jwt.dto.request.UpdateUserRequest;

public interface UpdateUserInfoUseCase {
    void execute(int userId, UpdateUserRequest updateUserRequest);
}
