package com.example.jwt.interface_adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.auth.AuthUsecase;
import com.example.jwt.dto.request.LoginRequest;
import com.example.jwt.dto.request.RegisterRequest;
import com.example.jwt.dto.response.AuthResponse;
import com.example.jwt.dto.response.ErrorResponse;
import com.example.jwt.dto.response.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthUsecase authUsecase;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authUsecase.register(registerRequest);

        if (!response.isSuccess()) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    response.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authUsecase.login(loginRequest);

        if (!authResponse.isSuccess()) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    authResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        LoginResponse loginResponse = LoginResponse.fromAuthResponse(authResponse);
        return ResponseEntity.ok(loginResponse);
    }
}