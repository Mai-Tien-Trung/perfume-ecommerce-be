package com.example.perfume_ecommerce.controller;

import com.example.perfume_ecommerce.dto.request.FragranceNoteRequest;
import com.example.perfume_ecommerce.dto.response.FragranceNoteResponse;
import com.example.perfume_ecommerce.service.FragranceNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class FragranceNoteController {

    private final FragranceNoteService noteService;

    // Thêm note vào product
    @PostMapping("/products/{productId}/notes")
    public ResponseEntity<FragranceNoteResponse> addNote(
            @PathVariable Long productId,
            @RequestBody FragranceNoteRequest dto) {
        return ResponseEntity.ok(noteService.addNote(productId, dto));
    }

    // Lấy notes theo product
    @GetMapping("/products/{productId}/notes")
    public ResponseEntity<List<FragranceNoteResponse>> getNotesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(noteService.getNotesByProduct(productId));
    }

    // Cập nhật note
    @PutMapping("/notes/{noteId}")
    public ResponseEntity<FragranceNoteResponse> updateNote(
            @PathVariable Long noteId,
            @RequestBody FragranceNoteRequest dto) {
        return ResponseEntity.ok(noteService.updateNote(noteId, dto));
    }

    // Xóa note
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}
