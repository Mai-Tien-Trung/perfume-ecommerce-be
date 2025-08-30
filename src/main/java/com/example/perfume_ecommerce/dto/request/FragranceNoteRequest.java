package com.example.perfume_ecommerce.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FragranceNoteRequest {
    private String type; // "TOP", "MIDDLE", "BASE"
    private String name;
}
