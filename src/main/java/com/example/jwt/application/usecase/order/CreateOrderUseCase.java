package com.example.jwt.application.usecase.order;

import com.example.jwt.dto.request.OrderRequest;

public interface CreateOrderUseCase {
    void execute(OrderRequest orderRequest);
}
