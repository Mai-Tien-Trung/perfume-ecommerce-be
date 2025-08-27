package com.example.perfume_ecommerce.repository;

import com.example.perfume_ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);

    List<ProductImage> findByProductIdOrderByOrderIndexAsc(Long productId);
}