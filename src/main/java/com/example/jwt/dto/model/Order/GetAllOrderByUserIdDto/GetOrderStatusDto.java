package com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto;

import com.example.jwt.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderStatusDto {
    private int orderId;
    private OrderStatus orderStatus;
}
