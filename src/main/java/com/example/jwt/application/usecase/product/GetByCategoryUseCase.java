package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.response.ObjectResponse;

public interface GetByCategoryUseCase {
    ObjectResponse<ProductDto> execute(int PageNo, int pageSize, String sortBy, String category);
}
