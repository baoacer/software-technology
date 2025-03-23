package com.example.jwt.application.usecase.order;

import java.util.List;

import com.example.jwt.dto.model.Order.CreateDto.CreateOrderOutputDto;
import com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto.GetAllOrderByUserIdOutputDto;
import com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto.GetOrderStatusDto;
import com.example.jwt.dto.request.Order.CancelOrderRequest;
import com.example.jwt.dto.request.Order.GetAllOrderByUserIdRequest;
import com.example.jwt.dto.request.Order.OrderRequest;

public interface IOrderUseCase {
    void cancelOrder(CancelOrderRequest cancelOrderRequest);

    CreateOrderOutputDto createOrder(OrderRequest orderRequest);

    List<GetAllOrderByUserIdOutputDto> getAllOrderByUserId(GetAllOrderByUserIdRequest getAllOrderByUserIdRequest);

    List<GetOrderStatusDto> getAllOrderStatusByUserId(int userId);
}
