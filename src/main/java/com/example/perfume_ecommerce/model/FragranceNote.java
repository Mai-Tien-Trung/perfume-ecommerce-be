package com.example.perfume_ecommerce.model;

import com.example.perfume_ecommerce.enums.NoteType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FragranceNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // TOP, MIDDLE, BASE
    @Column(nullable = false)
    private NoteType type;

    @Column(nullable = false, length = 100)
    private String name; // ví dụ: Lemon, Rose, Vanilla

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore

    private Product product;
}
