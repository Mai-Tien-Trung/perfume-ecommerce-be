package com.example.perfume_ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    // Thứ tự hiển thị: 0 = thumbnail, 1 = ảnh thứ 2, ...
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}