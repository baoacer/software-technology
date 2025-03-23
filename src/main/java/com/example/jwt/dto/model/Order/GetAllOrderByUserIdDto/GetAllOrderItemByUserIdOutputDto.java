package com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetAllOrderItemByUserIdOutputDto {
    private short productId;
    private int quantity;
    private BigDecimal price;
}
