package com.example.perfume_ecommerce.enums;

public enum OrderStatus {
    PENDING,      // Đơn mới tạo
    PAID,         // Đã thanh toán
    PROCESSING,   // Đang xử lý
    SHIPPED,      // Đã giao cho đơn vị vận chuyển
    DELIVERED,    // Khách đã nhận
    CANCELLED,    // Đã hủy đơn
    REFUNDED      // Đã hoàn tiền
}
