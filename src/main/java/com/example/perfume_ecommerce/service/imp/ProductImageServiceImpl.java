package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.model.Product;
import com.example.perfume_ecommerce.model.ProductImage;
import com.example.perfume_ecommerce.repository.ProductImageRepository;
import com.example.perfume_ecommerce.repository.ProductRepository;
import com.example.perfume_ecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductImage> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<ProductImage> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public ProductImage createImage(ProductImage image) {
        if (image.getProduct() != null && image.getProduct().getId() != null) {
            Product product = productRepository.findById(image.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            image.setProduct(product);
        }
        return imageRepository.save(image);
    }

    @Override
    public ProductImage updateImage(Long id, ProductImage imageDetails) {
        return imageRepository.findById(id).map(image -> {
            image.setImageUrl(imageDetails.getImageUrl());
            image.setOrderIndex(imageDetails.getOrderIndex());

            if (imageDetails.getProduct() != null) {
                Product product = productRepository.findById(imageDetails.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                image.setProduct(product);
            }

            return imageRepository.save(image);
        }).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    @Override
    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new RuntimeException("Image not found");
        }
        imageRepository.deleteById(id);
    }
}
