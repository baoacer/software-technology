package com.example.jwt.application.usecase.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.product.UpdateProductUseCase;
import com.example.jwt.dto.request.UpdateProductRequest;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateProductUseCaseImpl.class);

    @Override
    public void execute(short id, UpdateProductRequest updateProductRequest) {
        // Tìm sản phẩm theo ID
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product with id {} not found", id);
                    return new NotFoundException("Product not found, please try again later.");
                });

        // Cập nhật thông tin sản phẩm nếu các trường trong request không null
        if (updateProductRequest.getTitle() != null) {
            product.setTitle(updateProductRequest.getTitle());
        }
        if (updateProductRequest.getDescription() != null) {
            product.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getTags() != null) {
            product.setTags(updateProductRequest.getTags());
        }
        if (updateProductRequest.getPrice() != null) {
            product.setPrice(updateProductRequest.getPrice());
        }
        if (updateProductRequest.getCategoryId() != null) {
            // Giả sử có một phương thức để cập nhật categoryId (cần thêm logic xử lý)
            // product.setCategoryId(updateProductRequest.getCategoryId());
        }
        if (updateProductRequest.getVendorId() != null) {
            // Giả sử có một phương thức để cập nhật vendorId (cần thêm logic xử lý)
            // product.setVendorId(updateProductRequest.getVendorId());
        }

        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
        productRepository.save(product);
        logger.info("Product with id {} updated successfully", id);
    }
}