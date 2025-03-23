package com.example.jwt.dto.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private int id;
    private int order_id;
    private int product_id;
    private short quantity;
    private BigDecimal price; // Price at the time of order
}
