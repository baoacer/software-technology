package com.example.jwt.application.usecase.category;

import java.util.List;

import com.example.jwt.dto.model.CategoryDto;

public interface GetAllCategoryUseCase {
    List<CategoryDto> execute();
}