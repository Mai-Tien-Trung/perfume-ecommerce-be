package com.example.perfume_ecommerce.service;

import com.example.perfume_ecommerce.dto.request.FragranceNoteRequest;
import com.example.perfume_ecommerce.dto.response.FragranceNoteResponse;

import java.util.List;

public interface FragranceNoteService {
    FragranceNoteResponse addNote(Long productId, FragranceNoteRequest dto);

    List<FragranceNoteResponse> getNotesByProduct(Long productId);

    FragranceNoteResponse updateNote(Long noteId, FragranceNoteRequest dto);

    void deleteNote(Long noteId);
}
