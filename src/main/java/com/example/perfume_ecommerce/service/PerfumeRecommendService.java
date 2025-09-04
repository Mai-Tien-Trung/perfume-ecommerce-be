package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.response.RecommendResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class PerfumeRecommendService {
    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public PerfumeRecommendService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://rdemarqui-perfume-recommender.hf.space").build();
    }

    public RecommendResponse recommend(String brand, String perfume) throws Exception {
        // 1. POST để lấy event_id
        Map<String, Object> body = Map.of("data", List.of(brand, perfume));

        String response = webClient.post()
                .uri("/call/filter_by_perfume")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, Object> responseMap = mapper.readValue(response, new TypeReference<>() {});
        String eventId = (String) responseMap.get("event_id");

        // 2. GET để lấy SSE kết quả
        String rawResult = webClient.get()
                .uri("/call/filter_by_perfume/{eventId}", eventId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Debug log
        System.out.println("Raw SSE: " + rawResult);

        // Tách phần sau "data:"
        // Tách phần sau "data:"
        int dataIndex = rawResult.indexOf("data:");
        if (dataIndex == -1) {
            throw new RuntimeException("Không tìm thấy data trong SSE: " + rawResult);
        }
        String jsonPart = rawResult.substring(dataIndex + 5).trim();

// Parse JSON (là 1 array chứ không phải object)
        List<Map<String, Object>> parsedList = mapper.readValue(
                jsonPart, new TypeReference<List<Map<String, Object>>>() {}
        );

        Map<String, Object> first = parsedList.get(0);
        List<List<String>> rows = (List<List<String>>) first.get("data");

// Tạo DTO trả về
        return new RecommendResponse(perfume, rows.stream()
                .map(r -> r.get(1)) // lấy cột "perfume"
                .toList());
    }
}
