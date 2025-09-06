package com.example.perfume_ecommerce.repository;

import com.example.perfume_ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Lấy tất cả order của 1 user
    List<Order> findByUserId(Long userId);
}
