package com.example.jwt.interface_adapter.controller;

import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;

import com.example.jwt.application.usecase.order.IOrderUseCase;
import com.example.jwt.dto.model.Order.CreateDto.CreateOrderOutputDto;
import com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto.GetAllOrderByUserIdOutputDto;
import com.example.jwt.dto.request.Order.CancelOrderRequest;
import com.example.jwt.dto.request.Order.GetAllOrderByUserIdRequest;
import com.example.jwt.dto.request.Order.OrderItemRequest;
import com.example.jwt.dto.request.Order.OrderRequest;
import com.example.jwt.dto.response.Order.CancelOrderResponse;
import com.example.jwt.dto.response.Order.CreateOrderResponse;
import com.example.jwt.dto.response.Order.GetAllOrderResponse;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private IOrderUseCase orderUseCase;

    public OrderController(IOrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @SuppressWarnings("rawtypes")
    @PostMapping({ "/", "" })
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody OrderRequest order) {
        // Kiểm tra userId
        if (order.getUserId() == 0) {
            return ResponseEntity.badRequest().body(new CreateOrderResponse<>("User ID not provided", null));
        }

        // Kiểm tra shippingAddress
        if (order.getShippingAddress() == null || order.getShippingAddress().isBlank()) {
            return ResponseEntity.badRequest().body(new CreateOrderResponse<>("Shipping address not provided", null));
        }

        // kiểm tra paymentmethod
        if (order.getPaymentMethod() == null || order.getPaymentMethod().isBlank()) {
            return ResponseEntity.badRequest().body(new CreateOrderResponse<>("Payment method not provided", null));
        }

        // Kiểm tra orderItems
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty() || order.getOrderItems().size() == 0) {
            return ResponseEntity.badRequest().body(new CreateOrderResponse<>("Order items not provided", null));
        }

        // kiểm tra orderItems
        for (OrderItemRequest orderItem : order.getOrderItems()) {
            // Kiểm tra productId
            if (orderItem.getProductId() == 0) {
                return ResponseEntity.badRequest().body(new CreateOrderResponse<>("Product ID not provided", null));
            }
            // Kiểm tra quantity
            if (orderItem.getQuantity() == 0 || orderItem.getQuantity() < 0) {
                return ResponseEntity.badRequest().body(new CreateOrderResponse<>("Quantity not provided", null));
            }
        }

        try {
            CreateOrderOutputDto orderDto = this.orderUseCase.createOrder(order);
            return ResponseEntity.ok(new CreateOrderResponse<>("Order created successfully", null));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("User not found")) {
                return ((BodyBuilder) ResponseEntity.notFound()).body(new CreateOrderResponse<>(e.getMessage(), null));
            }
            if (e.getMessage().contains("Product not found")) {
                return ((BodyBuilder) ResponseEntity.notFound()).body(new CreateOrderResponse<>(e.getMessage(), null));
            }
            return ResponseEntity.badRequest().body(new CreateOrderResponse<>("Failed to create order", null));
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOder(@RequestBody CancelOrderRequest cancelOrderRequest) {
        if (cancelOrderRequest.getOrderId() == 0) {
            return ((BodyBuilder) ResponseEntity.notFound())
                    .body(new CancelOrderResponse<>("Order ID not provided", null));
        }
        try {
            this.orderUseCase.cancelOrder(cancelOrderRequest);
            return ResponseEntity.ok(new CancelOrderResponse<>("Order canceled successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CancelOrderResponse<>(e.getMessage(), null));
        }
    }

    @SuppressWarnings("rawtypes")
    @GetMapping({ "/", "" })
    public ResponseEntity<GetAllOrderResponse> getAllOrdersByUserId(
            @RequestBody GetAllOrderByUserIdRequest getAllOrderByUserIdRequest) {
        if (getAllOrderByUserIdRequest == null || getAllOrderByUserIdRequest.getUserId() == 0) {
            return ResponseEntity.badRequest().body(new GetAllOrderResponse<>("User ID not provided", null));
        }
        try {
            List<GetAllOrderByUserIdOutputDto> list = this.orderUseCase.getAllOrderByUserId(getAllOrderByUserIdRequest);
            return ResponseEntity.ok(new GetAllOrderResponse<>("Get Success", list));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GetAllOrderResponse<>(e.getMessage(), null));
        }
    }
}
