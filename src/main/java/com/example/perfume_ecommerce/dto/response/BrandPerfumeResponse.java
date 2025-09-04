package com.example.perfume_ecommerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BrandPerfumeResponse {
    private String brand;
    private List<String> perfumes;
}
