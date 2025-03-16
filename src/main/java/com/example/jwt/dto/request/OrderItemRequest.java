package com.example.jwt.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemRequest {
    private short productId;
    private short quantity;
    private BigDecimal price;
}
