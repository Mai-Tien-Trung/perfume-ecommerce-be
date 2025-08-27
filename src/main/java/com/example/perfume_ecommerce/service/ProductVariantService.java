package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.model.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface ProductVariantService {
    List<ProductVariant> getAllVariants();
    Optional<ProductVariant> getVariantById(Long id);
    ProductVariant createVariant(ProductVariant variant);
    ProductVariant updateVariant(Long id, ProductVariant variantDetails);
    void deleteVariant(Long id);
}