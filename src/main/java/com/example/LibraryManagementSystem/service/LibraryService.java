package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.repository.LibraryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final LibraryRepo repo;

    public LibraryService(LibraryRepo repo) {
        this.repo = repo;
    }

    // READ - toate bibliotecile
    public List<Library> getAll() {
        return repo.findAll();
    }

    // READ - o bibliotecă după ID
    public Library getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE & UPDATE
    public Library save(Library library) {
        return repo.save(library);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // Generare ID automat
    public String generateNextId() {
        return "LIB" + (repo.count() + 1);
    }

    // Helper pentru formular nou
    public Library newForForm() {
        Library library = new Library();
        library.setId(generateNextId());
        return library;
    }

    // --- Validări business ---

    // Verifică dacă există o bibliotecă cu acest nume (pentru CREATE)
    public boolean existsByName(String name) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCase(name.trim());
    }

    // Verifică dacă există altă bibliotecă cu acest nume (pentru UPDATE)
    public boolean existsByNameForOtherLibrary(String name, String excludedId) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCaseAndIdNot(name.trim(), excludedId);
    }
}