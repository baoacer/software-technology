package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.model.ProductDto;

public interface GetProductByIdUseCase {
    ProductDto execute(short id);
}