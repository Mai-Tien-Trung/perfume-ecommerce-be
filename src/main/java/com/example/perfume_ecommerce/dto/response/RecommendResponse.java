package com.example.perfume_ecommerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendResponse {
    private String basePerfume;
    private List<String> recommendations;
}
