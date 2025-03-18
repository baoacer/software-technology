package com.example.jwt.interface_adapter.controller;

import com.example.jwt.application.usecase.user.UpdateUserInfoUseCase;
import com.example.jwt.dto.model.UserDto;
import com.example.jwt.dto.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
