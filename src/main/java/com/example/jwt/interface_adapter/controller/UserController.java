package com.example.jwt.interface_adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.user.GetUserInfoUsecase;
import com.example.jwt.application.usecase.user.UpdateUserInfoUsecase;
import com.example.jwt.dto.request.UpdateUserRequest;
import com.example.jwt.dto.response.UserResponse;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private GetUserInfoUsecase getUserInfoUsecase;

    @Autowired
    private UpdateUserInfoUsecase updateUserInfoUsecase;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable String userId) {
        UserResponse userResponse = getUserInfoUsecase.execute(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserInfo(
            @PathVariable String userId,
            @RequestBody UpdateUserRequest updateUserRequest) {
        updateUserInfoUsecase.execute(userId, updateUserRequest);
        return ResponseEntity.ok().build();
    }
}
