package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.request.BrandRequest;
import com.example.perfume_ecommerce.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandById(Long id);
    BrandResponse createBrand(BrandRequest dto);
    BrandResponse updateBrand(Long id, BrandRequest dto);
    void deleteBrand(Long id);
}

