package com.example.jwt.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
    private int userId;
    private List<OrderItemRequest> orderItems;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private BigDecimal totalAmount;
}
