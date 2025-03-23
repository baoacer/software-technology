package com.example.jwt.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.auth.AuthUsecase;
import com.example.jwt.dto.mapper.UserMapper;
import com.example.jwt.dto.request.LoginRequest;
import com.example.jwt.dto.request.RegisterRequest;
import com.example.jwt.dto.response.AuthResponse;
import com.example.jwt.entities.CartEntity;
import com.example.jwt.entities.UserEntity;
import com.example.jwt.infra.repositories.UserRepository;
import com.example.jwt.utils.Role;

import jakarta.transaction.Transactional;

@Service
public class AuthService implements AuthUsecase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new AuthResponse(false, "Username already exists", null);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new AuthResponse(false, "Email already exists", null);
        }

        // Create new user
        UserEntity newUser = new UserEntity();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setAddress(registerRequest.getAddress());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setRole(Role.USER); // Default role

        // Create and associate a cart
        CartEntity cart = new CartEntity();
        cart.setUser(newUser);
        newUser.setCart(cart);

        // Save the user
        userRepository.save(newUser);

        return new AuthResponse(true, "User registered successfully", userMapper.toResponse(newUser));
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Find user by username
        return userRepository.findByUsername(loginRequest.getUsername())
                .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .map(user -> {
                    // Ensure user has a role
                    if (user.getRole() == null) {
                        user.setRole(Role.USER);
                        userRepository.save(user);
                    }
                    return new AuthResponse(true, "Login successful", userMapper.toResponse(user));
                })
                .orElse(new AuthResponse(false, "Invalid username or password", null));
    }
}