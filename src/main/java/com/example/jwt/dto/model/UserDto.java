package com.example.jwt.dto.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
        private int id;
        private String email;
        private String address;
        private String phone;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}
