package com.example.jwt.application.usecase.inventory;

import com.example.jwt.dto.model.InventoryDto;
import com.example.jwt.dto.request.CreateInventoryRequest;

public interface CreateInventoryUseCase {
    InventoryDto execute(CreateInventoryRequest request);
}
