package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.dto.request.BrandRequest;
import com.example.perfume_ecommerce.dto.response.BrandResponse;
import com.example.perfume_ecommerce.entity.Brand;
import com.example.perfume_ecommerce.repository.BrandRepository;
import com.example.perfume_ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImp implements BrandService {
    @Autowired
    private final BrandRepository brandRepository;

    private BrandResponse mapToResponse(Brand brand) {
        BrandResponse res = new BrandResponse();
        res.setId(brand.getId());
        res.setName(brand.getName());
        res.setCountry(brand.getCountry());
        res.setDescription(brand.getDescription());
        res.setLogoUrl(brand.getLogoUrl());
        return res;
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return mapToResponse(brand);
    }

    @Override
    public BrandResponse createBrand(BrandRequest dto) {
        Brand brand = new Brand();
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setDescription(dto.getDescription());
        brand.setLogoUrl(dto.getLogoUrl());
        return mapToResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest dto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setDescription(dto.getDescription());
        brand.setLogoUrl(dto.getLogoUrl());
        return mapToResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
