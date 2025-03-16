package com.example.jwt.infra.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jwt.entities.CartEntity;
import com.example.jwt.entities.CartItemEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    // Optional<CartEntity> findByUserId(String userId);

}
