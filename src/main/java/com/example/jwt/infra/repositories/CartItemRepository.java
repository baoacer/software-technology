package com.example.jwt.infra.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jwt.entities.CartItemEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    @Query(value = "select * from cart_items where user_id = :userId and product_id = :productId", nativeQuery = true)
    Optional<CartItemEntity> findByUserIdAndProductId(String userId, int productId);
}
