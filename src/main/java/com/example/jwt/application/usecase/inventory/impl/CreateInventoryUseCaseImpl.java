package com.example.jwt.application.usecase.inventory.impl;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.inventory.CreateInventoryUseCase;
import com.example.jwt.dto.model.InventoryDto;
import com.example.jwt.dto.request.CreateInventoryRequest;
import com.example.jwt.entities.InventoryEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.InventoryRepository;
import com.example.jwt.infra.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CreateInventoryUseCaseImpl implements CreateInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(CreateInventoryUseCaseImpl.class);

    @Override
    public InventoryDto execute(CreateInventoryRequest request) {
        try {
            ProductEntity product = this.productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            InventoryEntity inventory = new InventoryEntity();
            inventory.setProduct(product);
            inventory.setQuantity(request.getQuantity());
            inventory.setStatus(request.getStatus());

            InventoryEntity saveInventory = this.inventoryRepository.save(inventory);

            return InventoryDto.builder()
                    .id(saveInventory.getId())
                    .productId(saveInventory.getProduct().getId())
                    .quantity(saveInventory.getQuantity())
                    .status(saveInventory.getStatus())
                    .updatedAt(saveInventory.getUpdatedAt())
                    .build();

        } catch (RuntimeException e) {
            log.error("CreateInventoryUseCaseImpl Execute Error: {}", e.getMessage(), e);
            throw new RuntimeException("CreateInventoryUseCaseImpl Execute Error", e);
        }
    }
}
