package com.example.jwt.application.usecase.product;

import java.util.List;

import com.example.jwt.dto.model.SearchResponseDto;

public interface SearchProductUseCase {
    List<SearchResponseDto> execute(String query);
}
