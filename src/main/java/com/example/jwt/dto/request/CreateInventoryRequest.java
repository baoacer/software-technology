package com.example.jwt.dto.request;

import com.example.jwt.utils.InventoryStatus;
import lombok.Data;


@Data
public class CreateInventoryRequest {
    private Short productId;
    private Short quantity;
    private InventoryStatus status;
}
