package com.example.perfume_ecommerce.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long productVariantId;
    private String productName; // optional: lấy từ ProductVariant
    private Integer quantity;
    private Double price;
}
