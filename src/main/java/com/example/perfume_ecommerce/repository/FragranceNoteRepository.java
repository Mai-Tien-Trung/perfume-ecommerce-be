package com.example.perfume_ecommerce.repository;

import com.example.perfume_ecommerce.model.FragranceNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FragranceNoteRepository extends JpaRepository<FragranceNote, Long> {
    List<FragranceNote> findByProductId(Long productId);
}
