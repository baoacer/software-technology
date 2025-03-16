package com.example.jwt.dto.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {
    private int id;
    private String title;
    private BigDecimal price;
    private List<ImageDto> images;
}
