package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.response.BrandPerfumeResponse;
import com.example.perfume_ecommerce.entity.Brand;
import com.example.perfume_ecommerce.repository.BrandRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class PerfumeMetaService {
    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BrandRepository brandRepository;

    public PerfumeMetaService(WebClient.Builder builder, BrandRepository brandRepository) {
        this.webClient = builder.baseUrl("https://rdemarqui-perfume-recommender.hf.space").build();
        this.brandRepository = brandRepository;
    }

    /**
     * Lấy danh sách perfumes theo brand từ HuggingFace
     */
    public BrandPerfumeResponse getPerfumesByBrand(String brand) throws Exception {
        Map<String, Object> body = Map.of("data", List.of(brand));

        // 1. POST -> lấy eventId
        String response = webClient.post()
                .uri("/call/perfume_change")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, Object> map = mapper.readValue(response, new TypeReference<>() {});
        String eventId = (String) map.get("event_id");

        // 2. GET -> lấy SSE kết quả
        String rawResult = webClient.get()
                .uri("/call/perfume_change/{eventId}", eventId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        int dataIndex = rawResult.indexOf("data:");
        String jsonPart = rawResult.substring(dataIndex + 5).trim();

        List<Map<String, Object>> parsed = mapper.readValue(jsonPart, new TypeReference<>() {});
        List<String> perfumes = (List<String>) parsed.get(0).get("data");

        return new BrandPerfumeResponse(brand, perfumes);
    }

    /**
     * Lấy danh sách brand từ DB
     */
    public List<String> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(Brand::getName)
                .toList();
    }
}
