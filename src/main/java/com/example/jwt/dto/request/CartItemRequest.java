package com.example.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private String userId;
    private short quantity;
    private int productId;
}
