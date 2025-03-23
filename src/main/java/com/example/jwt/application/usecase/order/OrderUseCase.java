package com.example.jwt.application.usecase.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto.GetOrderStatusDto;
import org.springframework.stereotype.Service;

import com.example.jwt.dto.model.Order.CreateDto.CreateOrderOutputDto;
import com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto.GetAllOrderByUserIdOutputDto;
import com.example.jwt.dto.model.Order.GetAllOrderByUserIdDto.GetAllOrderItemByUserIdOutputDto;
import com.example.jwt.dto.request.Order.CancelOrderRequest;
import com.example.jwt.dto.request.Order.GetAllOrderByUserIdRequest;
import com.example.jwt.dto.request.Order.OrderItemRequest;
import com.example.jwt.dto.request.Order.OrderRequest;
import com.example.jwt.entities.OrderEntity;
import com.example.jwt.entities.OrderItemEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.entities.UserEntity;
import com.example.jwt.infra.repositories.OrderItemRepository;
import com.example.jwt.infra.repositories.OrderRepository;
import com.example.jwt.infra.repositories.ProductRepository;
import com.example.jwt.infra.repositories.UserRepository;
import com.example.jwt.utils.OrderStatus;

@Service
public class OrderUseCase implements IOrderUseCase {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public OrderUseCase(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void cancelOrder(CancelOrderRequest cancelOrderRequest) {
        // Kiếm order
        OrderEntity order = this.orderRepository.findById(cancelOrderRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        try {
            order.setOrderStatus(OrderStatus.CANCELLED);
            this.orderRepository.save(order);
            return;
        } catch (Exception e) {
            throw new RuntimeException("Failed to cancel order");
        }
    }

    @Override
    public CreateOrderOutputDto createOrder(OrderRequest order) {
        // Kiểm tra User tồn tại
        UserEntity userEntity = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo Order
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setPaymentMethod(order.getPaymentMethod());
        orderEntity.setShippingAddress(order.getShippingAddress());
        orderEntity.setUser(userEntity);

        // Lưu Order vào DB trước để có ID
        orderEntity = orderRepository.save(orderEntity);
        // Xử lý OrderItem
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItem : order.getOrderItems()) {
            Short productId = Short.valueOf(orderItem.getProductId());
            List<Short> allProductIds = productRepository.findAll().stream()
                    .map(ProductEntity::getId)
                    .collect(Collectors.toList());
            System.out.println("📌 Kiểu dữ liệu của ID: " + ((Object) orderItem.getProductId()).getClass().getName());
            System.out.println("Danh sách product ID trong DB: " + allProductIds);

            System.out.println("Product id duoc cung cap tu client " + orderItem.getProductId());
            ProductEntity productEntity = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(orderEntity);
            orderItemEntity.setProduct(productEntity);
            orderItemEntity.setQuantity(Short.valueOf(String.valueOf(orderItem.getProductId())));

            orderItems.add(orderItemEntity);
        }

        // Lưu tất cả OrderItems một lần để tối ưu hiệu suất
        orderItemRepository.saveAll(orderItems);

        // Tạo DTO phản hồi
        CreateOrderOutputDto orderDto = new CreateOrderOutputDto();
        orderDto.setMessage("Order created successfully");
        return orderDto;
    }

    @Override
    public List<GetAllOrderByUserIdOutputDto> getAllOrderByUserId(
            GetAllOrderByUserIdRequest getAllOrderByUserIdRequest) {
        if (getAllOrderByUserIdRequest == null || getAllOrderByUserIdRequest.getUserId() == 0) {
            throw new RuntimeException("User ID not provided");
        }

        // Lấy danh sách đơn hàng theo User ID
        List<OrderEntity> orderEntities = orderRepository.findByUserId(getAllOrderByUserIdRequest.getUserId());

        // System.err.println(orderEntities);
        // if (!orderEntities.isEmpty()) {
        // List<OrderItemEntity> orderItemEntities =
        // orderEntities.get(0).getOrderItems();
        // for (OrderItemEntity orderItemEntity : orderItemEntities) {
        // System.err.println(orderItemEntity.toString());
        // }
        // }

        // Chuyển đổi danh sách OrderEntity thành OrderOutputDto
        return orderEntities.stream().map(orderEntity -> {
            GetAllOrderByUserIdOutputDto orderOutputDto = new GetAllOrderByUserIdOutputDto();
            orderOutputDto.setOrderId(orderEntity.getId());
            orderOutputDto.setPaymentMethod(orderEntity.getPaymentMethod());
            orderOutputDto.setShippingAddress(orderEntity.getShippingAddress());

            // Lấy danh sách OrderItem theo Order ID và map sang DTO
            List<GetAllOrderItemByUserIdOutputDto> orderItemOutputDtos = orderItemRepository
                    .findByOrderId(orderEntity.getId())
                    .stream()
                    .map(orderItemEntity -> new GetAllOrderItemByUserIdOutputDto(
                            orderItemEntity.getProduct().getId(),
                            orderItemEntity.getQuantity(),
                            orderItemEntity.getProduct().getPrice()
                                    .multiply(BigDecimal.valueOf(orderItemEntity.getQuantity()))))
                    .collect(Collectors.toList());

            orderOutputDto.setOrderItems(orderItemOutputDtos);
            orderOutputDto.setTotalAmount(orderEntity.calculateTotalPrice()); 
            return orderOutputDto;
        }).collect(Collectors.toList());

    }
    @Override
    public List<GetOrderStatusDto> getAllOrderStatusByUserId(int userId) {
        if (userId == 0) {
            throw new RuntimeException("User ID not provided");
        }

        // Lấy danh sách đơn hàng của user
        List<OrderEntity> orderEntities = orderRepository.findByUserId(userId);

        // Chỉ trả về orderId và orderStatus
        return orderEntities.stream()
                .map(order -> new GetOrderStatusDto(order.getId(), order.getOrderStatus()))
                .collect(Collectors.toList());
    }



}
