package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.request.AddToCartRequest;
import com.example.perfume_ecommerce.dto.request.UpdateCartRequest;
import com.example.perfume_ecommerce.dto.response.CartDTO;

public interface CartService {
    CartDTO getCart();
    CartDTO addItem(AddToCartRequest request);
    CartDTO updateItem(Long itemId, UpdateCartRequest request);
    CartDTO removeItem(Long itemId);
    CartDTO clearCart();
}

