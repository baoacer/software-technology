package com.example.jwt.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.entities.ProductEntity;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductMapper {
    private ModelMapper modelMapper;

    public ProductDto mapToDto(ProductEntity product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
