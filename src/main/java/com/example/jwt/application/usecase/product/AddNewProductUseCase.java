package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.request.CreateProductRequest;

public interface AddNewProductUseCase {
    void execute(CreateProductRequest createProductRequest);
}
