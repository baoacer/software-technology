package com.example.jwt.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.jwt.entities.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    // Optional<CartEntity> findByUserId(String userId);

}
