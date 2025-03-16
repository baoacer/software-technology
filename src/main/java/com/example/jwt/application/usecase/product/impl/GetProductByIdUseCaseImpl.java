package com.example.jwt.application.usecase.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.product.GetProductByIdUseCase;
import com.example.jwt.dto.mapper.ProductMapper;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {
    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(GetByCategoryUseCaseImpl.class);

    @Override
    public ProductDto execute(short id) {
        return this.productRepository.findById(id)
                .map(product -> {
                    ProductDto productDto = this.productMapper.mapToDto(product);
                    productDto.setVendor(product.getVendorEntity().getName());
                    productDto.setCategory(product.getCategoryEntity().getName());
                    return productDto;
                })
                .orElseThrow(() -> {
                    logger.warn("Product with id {} not found", id);
                    return new NotFoundException("Product not found, please try again later.");
                });
    }
}
