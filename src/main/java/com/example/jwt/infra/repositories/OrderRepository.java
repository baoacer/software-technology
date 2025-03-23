package com.example.jwt.infra.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.entities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query("SELECT * FROM orders WHERE user_id = :userId")
    List<OrderEntity> findByUserId(int userId);
}
