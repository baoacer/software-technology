package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.request.CartItemRequest;

public interface AddCartItemUseCase {
    void execute(CartItemRequest cartItemRequest);
}
