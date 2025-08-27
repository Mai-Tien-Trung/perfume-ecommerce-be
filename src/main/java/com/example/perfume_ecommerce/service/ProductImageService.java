package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.model.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    List<ProductImage> getAllImages();
    Optional<ProductImage> getImageById(Long id);
    ProductImage createImage(ProductImage image);
    ProductImage updateImage(Long id, ProductImage imageDetails);
    void deleteImage(Long id);
}