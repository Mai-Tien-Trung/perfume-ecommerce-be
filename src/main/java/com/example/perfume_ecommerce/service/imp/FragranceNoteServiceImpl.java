package com.example.perfume_ecommerce.service.imp;

import com.example.perfume_ecommerce.dto.request.FragranceNoteRequest;
import com.example.perfume_ecommerce.dto.response.FragranceNoteResponse;
import com.example.perfume_ecommerce.enums.NoteType;
import com.example.perfume_ecommerce.model.FragranceNote;
import com.example.perfume_ecommerce.model.Product;
import com.example.perfume_ecommerce.repository.FragranceNoteRepository;
import com.example.perfume_ecommerce.repository.ProductRepository;
import com.example.perfume_ecommerce.service.FragranceNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FragranceNoteServiceImpl implements FragranceNoteService {

    private final FragranceNoteRepository noteRepository;
    private final ProductRepository productRepository;

    // 1. Thêm note mới vào product
    public FragranceNoteResponse addNote(Long productId, FragranceNoteRequest dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        FragranceNote note = new FragranceNote();
        note.setType(NoteType.valueOf(dto.getType().toUpperCase())); // TOP/MIDDLE/BASE
        note.setName(dto.getName());
        note.setProduct(product);

        return mapToResponse(noteRepository.save(note));
    }

    // 2. Lấy notes theo productId
    public List<FragranceNoteResponse> getNotesByProduct(Long productId) {
        return noteRepository.findByProductId(productId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 3. Cập nhật note
    public FragranceNoteResponse updateNote(Long noteId, FragranceNoteRequest dto) {
        FragranceNote note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setType(NoteType.valueOf(dto.getType().toUpperCase()));
        note.setName(dto.getName());

        return mapToResponse(noteRepository.save(note));
    }

    // 4. Xóa note
    public void deleteNote(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new RuntimeException("Note not found");
        }
        noteRepository.deleteById(noteId);
    }

    // --- Mapper ---
    private FragranceNoteResponse mapToResponse(FragranceNote note) {
        FragranceNoteResponse res = new FragranceNoteResponse();
        res.setId(note.getId());
        res.setType(note.getType().name());
        res.setName(note.getName());
        return res;
    }
}
