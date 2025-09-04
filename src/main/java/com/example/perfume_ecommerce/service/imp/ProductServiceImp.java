package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.dto.request.ProductRequest;
import com.example.perfume_ecommerce.dto.response.ProductResponse;
import com.example.perfume_ecommerce.entity.Brand;
import com.example.perfume_ecommerce.entity.Product;
import com.example.perfume_ecommerce.repository.BrandRepository;
import com.example.perfume_ecommerce.repository.ProductRepository;
import com.example.perfume_ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final BrandRepository brandRepository;

    private ProductResponse mapToResponse(Product product) {
        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setFragranceFamily(product.getFragranceFamily());
        res.setGender(product.getGender());
        res.setReleaseYear(product.getReleaseYear());
        res.setThumbnailUrl(product.getThumbnailUrl());
        res.setBrandName(product.getBrand().getName());
        return res;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest dto) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setFragranceFamily(dto.getFragranceFamily());
        product.setGender(dto.getGender());
        product.setReleaseYear(dto.getReleaseYear());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setBrand(brand);

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setFragranceFamily(dto.getFragranceFamily());
        product.setGender(dto.getGender());
        product.setReleaseYear(dto.getReleaseYear());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setBrand(brand);

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
