package com.example.perfume_ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String fragranceFamily;
    private String gender;
    private Integer releaseYear;
    private String thumbnailUrl;
    private String brandName;
}
