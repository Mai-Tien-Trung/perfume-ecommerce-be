package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.model.Brand;
import com.example.perfume_ecommerce.model.Product;
import com.example.perfume_ecommerce.repository.BrandRepository;
import com.example.perfume_ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // kiểm tra brand hợp lệ
        if (product.getBrand() != null && product.getBrand().getId() != null) {
            Brand brand = brandRepository.findById(product.getBrand().getId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setFragranceFamily(productDetails.getFragranceFamily());
            product.setGender(productDetails.getGender());
            product.setReleaseYear(productDetails.getReleaseYear());
            product.setThumbnailUrl(productDetails.getThumbnailUrl());

            if (productDetails.getBrand() != null) {
                Brand brand = brandRepository.findById(productDetails.getBrand().getId())
                        .orElseThrow(() -> new RuntimeException("Brand not found"));
                product.setBrand(brand);
            }

            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
