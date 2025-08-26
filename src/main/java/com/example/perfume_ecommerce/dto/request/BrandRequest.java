package com.example.perfume_ecommerce.dto.request;

import lombok.Data;

@Data
public class BrandRequest {
    private Long id;
    private String name;
    private String country;
    private String description;
    private String logoUrl;
}
