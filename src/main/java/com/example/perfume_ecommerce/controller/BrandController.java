package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.entity.Brand;
import com.example.perfume_ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class BrandController {
@Autowired
    private final BrandRepository brandRepository;

    @GetMapping
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return brandRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        return brandRepository.save(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brandDetails) {
        return brandRepository.findById(id).map(brand -> {
            brand.setName(brandDetails.getName());
            brand.setCountry(brandDetails.getCountry());
            brand.setDescription(brandDetails.getDescription());
            brand.setLogoUrl(brandDetails.getLogoUrl());
            return ResponseEntity.ok(brandRepository.save(brand));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (!brandRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        brandRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
