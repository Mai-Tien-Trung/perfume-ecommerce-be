package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.dto.response.OrderResponse;
import com.example.perfume_ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderStaffController {

    private final OrderService orderService;

    // Lấy tất cả order (staff có quyền xem toàn bộ)
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Cập nhật trạng thái order
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }

    // Xoá đơn hàng
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully by staff");
    }
}
