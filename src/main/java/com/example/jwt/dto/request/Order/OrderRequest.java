package com.example.jwt.dto.request.Order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequest {
    private int userId;  
    private String shippingAddress;
    private String paymentMethod;
    private List<OrderItemRequest> orderItems;
}

