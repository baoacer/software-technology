package com.example.jwt.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductRequest {
    @NotBlank
    private String title;

    private String description;

    @Nullable
    private String tags;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Byte categoryId;

    @NotNull
    private Byte vendorId;

    @NotNull
    private Short inventoryId;

    private List<String> imageUrls;

}
