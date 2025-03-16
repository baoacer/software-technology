package com.example.jwt.application.usecase.product;

import java.util.List;
import com.example.jwt.dto.model.ProductDto;

public interface GetAllCartUseCase {
    List<ProductDto> execute(String userId);
}
