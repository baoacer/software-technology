package com.example.jwt.dto.request;

import java.math.BigDecimal;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class UpdateProductRequest {
    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String tags;

    @Nullable
    private BigDecimal price;

    @Nullable
    private Short categoryId;

    @Nullable
    private Short vendorId;
}
