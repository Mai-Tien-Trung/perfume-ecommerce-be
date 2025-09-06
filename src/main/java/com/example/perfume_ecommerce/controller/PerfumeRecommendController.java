package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.dto.response.RecommendResponse;
import com.example.perfume_ecommerce.service.PerfumeRecommendService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
@CrossOrigin(origins = "*")

public class PerfumeRecommendController {
    private final PerfumeRecommendService service;

    public PerfumeRecommendController(PerfumeRecommendService service) {
        this.service = service;
    }

    @GetMapping
    public RecommendResponse recommend(
            @RequestParam String brand,
            @RequestParam String perfume
    ) throws Exception {
        return service.recommend(brand, perfume);
    }
}
