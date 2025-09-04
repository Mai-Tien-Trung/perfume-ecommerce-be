package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.entity.Product;
import com.example.perfume_ecommerce.entity.ProductVariant;
import com.example.perfume_ecommerce.repository.ProductRepository;
import com.example.perfume_ecommerce.repository.ProductVariantRepository;
import com.example.perfume_ecommerce.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductVariant> getAllVariants() {
        return variantRepository.findAll();
    }

    @Override
    public Optional<ProductVariant> getVariantById(Long id) {
        return variantRepository.findById(id);
    }

    @Override
    public ProductVariant createVariant(ProductVariant variant) {
        if (variant.getProduct() != null && variant.getProduct().getId() != null) {
            Product product = productRepository.findById(variant.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            variant.setProduct(product);
        }
        return variantRepository.save(variant);
    }

    @Override
    public ProductVariant updateVariant(Long id, ProductVariant variantDetails) {
        return variantRepository.findById(id).map(variant -> {
            variant.setVolume(variantDetails.getVolume());
            variant.setPrice(variantDetails.getPrice());
            variant.setStockQuantity(variantDetails.getStockQuantity());
            variant.setSku(variantDetails.getSku());

            if (variantDetails.getProduct() != null) {
                Product product = productRepository.findById(variantDetails.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                variant.setProduct(product);
            }

            return variantRepository.save(variant);
        }).orElseThrow(() -> new RuntimeException("Variant not found"));
    }

    @Override
    public void deleteVariant(Long id) {
        if (!variantRepository.existsById(id)) {
            throw new RuntimeException("Variant not found");
        }
        variantRepository.deleteById(id);
    }
}
