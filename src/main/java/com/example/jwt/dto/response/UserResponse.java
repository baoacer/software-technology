package com.example.jwt.dto.response;

import com.example.jwt.utils.Role;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String username;
    private String email;
    private String address;
    private String phone;
    private Role role;
}