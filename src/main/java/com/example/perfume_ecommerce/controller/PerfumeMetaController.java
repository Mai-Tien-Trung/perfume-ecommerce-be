package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.dto.response.BrandPerfumeResponse;
import com.example.perfume_ecommerce.service.PerfumeMetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meta")
public class PerfumeMetaController {
    private final PerfumeMetaService service;

    public PerfumeMetaController(PerfumeMetaService service) {
        this.service = service;
    }

    @GetMapping("/brands")
    public List<String> getBrands() {
        return service.getBrands();
    }

    @GetMapping("/brands/{brand}/perfumes")
    public BrandPerfumeResponse getPerfumesByBrand(@PathVariable String brand) throws Exception {
        return service.getPerfumesByBrand(brand);
    }
}
