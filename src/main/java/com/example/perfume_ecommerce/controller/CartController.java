package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.dto.request.AddToCartRequest;
import com.example.perfume_ecommerce.dto.request.UpdateCartRequest;
import com.example.perfume_ecommerce.dto.response.CartDTO;
import com.example.perfume_ecommerce.service.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class CartController {

    private final CartService cartService;

    // Lấy giỏ hàng hiện tại của user (từ token)
    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/add")
    public ResponseEntity<CartDTO> addItem(@RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addItem(request));
    }

    // Cập nhật số lượng 1 sản phẩm trong giỏ
    @PutMapping("/update/{itemId}")
    public ResponseEntity<CartDTO> updateItem(@PathVariable Long itemId,
                                              @RequestBody UpdateCartRequest request) {
        return ResponseEntity.ok(cartService.updateItem(itemId, request));
    }

    // Xóa 1 sản phẩm khỏi giỏ
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<CartDTO> removeItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(itemId));
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }
}