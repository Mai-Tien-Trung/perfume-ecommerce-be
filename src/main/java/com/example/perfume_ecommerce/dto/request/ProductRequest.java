package com.example.perfume_ecommerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private String fragranceFamily;
    private String gender;
    private Integer releaseYear;
    private String thumbnailUrl;
    private Long brandId;
}