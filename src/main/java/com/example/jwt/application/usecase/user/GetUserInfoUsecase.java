package com.example.jwt.application.usecase.user;

import com.example.jwt.dto.response.UserResponse;

public interface GetUserInfoUsecase {
    UserResponse execute(String userId);
}