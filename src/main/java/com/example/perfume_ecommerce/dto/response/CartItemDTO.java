package com.example.perfume_ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long productVariantId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private double price;
    private double subtotal;
}