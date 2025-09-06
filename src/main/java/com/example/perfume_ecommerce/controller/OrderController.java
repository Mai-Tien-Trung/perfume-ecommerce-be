package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.dto.request.CheckoutRequest;
import com.example.perfume_ecommerce.dto.response.OrderResponse;
import com.example.perfume_ecommerce.security.CustomUserDetails;
import com.example.perfume_ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    // Checkout (tạo order mới từ cart)
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest request) {
        Long userId = getCurrentUserId(); // ✅ lấy từ token
        OrderResponse response = orderService.checkout(userId, request);
        return ResponseEntity.ok(response);
    }


    // Lấy 1 order theo id
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Lấy tất cả order của user hiện tại
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(orderService.getOrdersByUser(getCurrentUserId()));
    }

    // ---------------- Helper ----------------
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }

        if (principal instanceof String s && s.equals("anonymousUser")) {
            throw new RuntimeException("Anonymous user cannot access orders");
        }

        throw new RuntimeException("Unsupported principal type: " + principal.getClass().getName());
    }
}
