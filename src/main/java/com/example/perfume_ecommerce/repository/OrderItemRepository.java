package com.example.perfume_ecommerce.repository;

import com.example.perfume_ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Lấy tất cả order item theo orderId
    List<OrderItem> findByOrderId(Long orderId);
}
