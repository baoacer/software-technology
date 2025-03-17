package com.example.jwt.dto.model;

import com.example.jwt.utils.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    private short id;
    private short quantity;
    private InventoryStatus status;
    private LocalDate updatedAt;
    private short productId;
}
