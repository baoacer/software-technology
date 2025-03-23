package com.example.jwt.interface_adapter.controller;

import com.example.jwt.application.usecase.user.UpdateUserInfoUseCase;
import com.example.jwt.dto.model.UserDto;
import com.example.jwt.dto.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@AllArgsConstructor
public class UserController {


    private UpdateUserInfoUseCase updateUserInfoUseCase;

    @PutMapping
    public ResponseEntity<UserDto> updateUser(
            @RequestParam int userId,
            @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(this.updateUserInfoUseCase.execute(userId, request), HttpStatus.OK);
    }


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
