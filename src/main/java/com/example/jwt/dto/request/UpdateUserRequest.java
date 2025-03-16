package com.example.jwt.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String address;
    private String phone;
    private String email;
}
