package com.example.perfume_ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;
    private String description;
    private String fragranceFamily;
    private String gender;
    private Integer releaseYear;
    private String thumbnailUrl;
    @NotNull(message = "BrandId is required")
    private Long brandId;
}