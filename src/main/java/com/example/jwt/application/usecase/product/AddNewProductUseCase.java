package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.request.CreateProductRequest;

public interface AddNewProductUseCase {
    ProductDto execute(CreateProductRequest createProductRequest);
}
