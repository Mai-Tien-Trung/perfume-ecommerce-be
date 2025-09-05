package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.dto.request.AddToCartRequest;
import com.example.perfume_ecommerce.dto.request.UpdateCartRequest;
import com.example.perfume_ecommerce.dto.response.CartDTO;
import com.example.perfume_ecommerce.dto.response.CartItemDTO;
import com.example.perfume_ecommerce.entity.Cart;
import com.example.perfume_ecommerce.entity.CartItem;
import com.example.perfume_ecommerce.entity.ProductVariant;
import com.example.perfume_ecommerce.repository.CartItemRepository;
import com.example.perfume_ecommerce.repository.CartRepository;
import com.example.perfume_ecommerce.repository.ProductVariantRepository;
import com.example.perfume_ecommerce.security.CustomUserDetails;
import com.example.perfume_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;

    // Lấy userId từ SecurityContext
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }

        if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
            // fallback nếu Spring trả về user mặc định
            throw new RuntimeException("Unexpected Spring Security User, expected CustomUserDetails");
        }

        if (principal instanceof String s && s.equals("anonymousUser")) {
            throw new RuntimeException("Anonymous user cannot access cart");
        }

        throw new RuntimeException("Unsupported principal type: " + principal.getClass().getName());
    }


    @Override
    public CartDTO getCart() {
        Long userId = getCurrentUserId();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(null, userId, new ArrayList<>())));

        return mapToDTO(cart);
    }

    @Override
    public CartDTO addItem(AddToCartRequest request) {
        Long userId = getCurrentUserId();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(null, userId, new ArrayList<>())));

        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        // check if item exists
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductVariantId().equals(request.getProductVariantId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductVariantId(request.getProductVariantId());
            newItem.setQuantity(request.getQuantity());
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        return mapToDTO(cart);
    }

    @Override
    public CartDTO updateItem(Long itemId, UpdateCartRequest request) {
        Long userId = getCurrentUserId();

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getUserId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);

        return mapToDTO(item.getCart());
    }

    @Override
    public CartDTO removeItem(Long itemId) {
        Long userId = getCurrentUserId();

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getUserId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        Cart cart = item.getCart();
        cartItemRepository.delete(item);
        cart.getItems().remove(item);

        return mapToDTO(cart);
    }

    @Override
    public CartDTO clearCart() {
        Long userId = getCurrentUserId();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);

        return new CartDTO(userId, List.of(), 0);
    }

    // Helper: mapping Cart -> CartDTO
    private CartDTO mapToDTO(Cart cart) {
        List<CartItemDTO> items = cart.getItems().stream().map(item -> {
            ProductVariant variant = productVariantRepository.findById(item.getProductVariantId())
                    .orElseThrow(() -> new RuntimeException("Variant not found"));
            return new CartItemDTO(
                    item.getId(),
                    variant.getId(),
                    variant.getProduct().getName(),
                    variant.getProduct().getThumbnailUrl(),
                    item.getQuantity(),
                    variant.getPrice(),
                    variant.getPrice() * item.getQuantity()
            );
        }).toList();

        double total = items.stream().mapToDouble(CartItemDTO::getSubtotal).sum();

        return new CartDTO(cart.getUserId(), items, total);
    }
}
