package com.example.jwt.application.usecase.user;

import com.example.jwt.dto.request.UpdateUserRequest;

public interface UpdateUserInfoUsecase {
    void execute(String userId, UpdateUserRequest updateUserRequest);
}
