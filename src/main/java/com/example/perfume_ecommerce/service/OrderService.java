package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.request.CheckoutRequest;
import com.example.perfume_ecommerce.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(Long userId, CheckoutRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByUser(Long userId);
}
