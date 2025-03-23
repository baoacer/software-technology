package com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetAllOrderByUserIdOutputDto {
    private int orderId;
    private String shippingAddress;
    private String paymentMethod;
    private List<GetAllOrderItemByUserIdOutputDto> orderItems;
    private BigDecimal totalAmount;
}
