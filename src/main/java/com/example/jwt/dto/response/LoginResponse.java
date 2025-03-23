package com.example.jwt.dto.response;

import com.example.jwt.utils.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
    private int userId;
    private Role role;

    public static LoginResponse fromAuthResponse(AuthResponse authResponse) {
        if (authResponse == null || !authResponse.isSuccess() || authResponse.getUser() == null) {
            return new LoginResponse(false,
                    authResponse != null ? authResponse.getMessage() : "Login failed",
                    0, null);
        }

        return new LoginResponse(
                true,
                authResponse.getMessage(),
                authResponse.getUser().getId(),
                authResponse.getUser().getRole());
    }
}