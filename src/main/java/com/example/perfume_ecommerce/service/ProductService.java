package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.request.ProductRequest;
import com.example.perfume_ecommerce.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest dto);
    ProductResponse updateProduct(Long id, ProductRequest dto);
    void deleteProduct(Long id);
}