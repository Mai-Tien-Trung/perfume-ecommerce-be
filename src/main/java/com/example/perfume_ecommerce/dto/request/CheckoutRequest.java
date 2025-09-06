package com.example.perfume_ecommerce.dto.request;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {
    private String shippingAddress;
    private List<OrderItemRequest> items;
}
