package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.model.Product;
import com.example.perfume_ecommerce.model.ProductImage;
import com.example.perfume_ecommerce.repository.ProductImageRepository;
import com.example.perfume_ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ProductImageController {

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;

    @GetMapping("/product/{productId}")
    public List<ProductImage> getImagesByProduct(@PathVariable Long productId) {
        return imageRepository.findByProductIdOrderByOrderIndexAsc(productId);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ProductImage> createImage(@PathVariable Long productId,
                                                    @RequestBody ProductImage image) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        image.setProduct(product);
        return ResponseEntity.ok(imageRepository.save(image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImage> updateImage(@PathVariable Long id,
                                                    @RequestBody ProductImage imageDetails) {
        return imageRepository.findById(id).map(image -> {
            image.setImageUrl(imageDetails.getImageUrl());
            image.setOrderIndex(imageDetails.getOrderIndex());
            return ResponseEntity.ok(imageRepository.save(image));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        if (!imageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        imageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
