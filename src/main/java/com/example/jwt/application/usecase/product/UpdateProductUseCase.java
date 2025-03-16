package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.request.UpdateProductRequest;

public interface UpdateProductUseCase {
    void execute(short id, UpdateProductRequest updateProductRequest);
}
