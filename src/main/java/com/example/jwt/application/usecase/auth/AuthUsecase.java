package com.example.jwt.application.usecase.auth;

import com.example.jwt.dto.request.LoginRequest;
import com.example.jwt.dto.request.RegisterRequest;
import com.example.jwt.dto.response.AuthResponse;

public interface AuthUsecase {
    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}