package com.example.jwt.application.usecase.product;

import java.util.List;

import com.example.jwt.dto.model.ProductDto;

public interface GetUserCartUseCase {
    List<ProductDto> execute(String userId);
}
