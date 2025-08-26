package com.example.perfume_ecommerce.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {
    private Long id;
    private String name;
    private String country;
    private String description;
    private String logoUrl;
}
