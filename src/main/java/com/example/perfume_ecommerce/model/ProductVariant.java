package com.example.perfume_ecommerce.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_variant")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer volume;       // ml
    private Double price;
    private Integer stockQuantity;
    private String sku;           // mã hàng

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}