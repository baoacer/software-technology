package com.example.jwt.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.jwt.entities.CartEntity;
import com.example.jwt.entities.UserEntity;
import com.example.jwt.infra.repositories.UserRepository;
import com.example.jwt.utils.Role;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.email:admin@techshop.com}")
    private String adminEmail;

    @Value("${admin.default-password:admin123}")
    private String adminDefaultPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Initializing data...");

        // Initialize roles for any users that don't have a role
        List<UserEntity> users = userRepository.findAll();
        int updatedUsers = 0;

        for (UserEntity user : users) {
            if (user.getRole() == null) {
                user.setRole(Role.USER); // Set default role to USER
                userRepository.save(user);
                updatedUsers++;
            }
        }

        if (updatedUsers > 0) {
            log.info("Updated {} users with default role", updatedUsers);
        } else {
            log.info("No users needed role updates");
        }

        // Check if admin account exists, create if not
        createAdminAccountIfNotExists();
    }

    @Transactional
    private void createAdminAccountIfNotExists() {
        // Check if any admin account exists
        boolean adminExists = userRepository.existsByRole(Role.ADMIN);

        if (!adminExists) {
            log.info("Creating default admin account...");
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername(adminUsername);
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminDefaultPassword));
            adminUser.setRole(Role.ADMIN);

            // Create and associate a cart
            CartEntity cart = new CartEntity();
            cart.setUser(adminUser);
            adminUser.setCart(cart);

            userRepository.save(adminUser);
            log.info("Admin account created successfully with username: {}", adminUsername);
        } else {
            log.info("Admin account already exists");
        }
    }
}