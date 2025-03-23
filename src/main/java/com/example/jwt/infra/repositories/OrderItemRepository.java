package com.example.jwt.infra.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.entities.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
        @Query("SELECT * FROM order_items WHERE order_id = :orderId")
        List<OrderItemEntity> findByOrderId(int orderId);
}
