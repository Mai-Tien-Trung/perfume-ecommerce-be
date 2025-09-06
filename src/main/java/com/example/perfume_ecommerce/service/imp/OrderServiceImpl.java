package com.example.perfume_ecommerce.service.imp;


import com.example.perfume_ecommerce.dto.request.CheckoutRequest;
import com.example.perfume_ecommerce.dto.response.OrderItemResponse;
import com.example.perfume_ecommerce.dto.response.OrderResponse;

import com.example.perfume_ecommerce.entity.Order;
import com.example.perfume_ecommerce.entity.OrderItem;
import com.example.perfume_ecommerce.entity.ProductVariant;
import com.example.perfume_ecommerce.enums.OrderStatus;
import com.example.perfume_ecommerce.repository.OrderRepository;
import com.example.perfume_ecommerce.repository.ProductVariantRepository;
import com.example.perfume_ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public OrderResponse checkout(Long userId, CheckoutRequest request) {
        // Tạo Order mới
        Order order = Order.builder()
                .userId(userId)
                .shippingAddress(request.getShippingAddress())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();


        // Chuyển từ request → OrderItem
        List<OrderItem> orderItems = request.getItems().stream().map(itemReq -> {
            ProductVariant variant = productVariantRepository.findById(itemReq.getProductVariantId())
                    .orElseThrow(() -> new RuntimeException("ProductVariant not found: " + itemReq.getProductVariantId()));

            double price = variant.getPrice(); // freeze giá
            return OrderItem.builder()
                    .order(order)
                    .productVariantId(variant.getId())
                    .quantity(itemReq.getQuantity())
                    .price(price)
                    .build();
        }).collect(Collectors.toList());

// tính tổng tiền
        double total = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ------------------ Mapper ------------------

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream().map(item ->
                OrderItemResponse.builder()
                        .productVariantId(item.getProductVariantId())
                        .productName("Tên sản phẩm placeholder") // TODO: map từ ProductVariant
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build()
        ).collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .shippingAddress(order.getShippingAddress())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status)); // convert String -> Enum
        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

}

