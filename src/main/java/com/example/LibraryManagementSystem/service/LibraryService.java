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

    public List<Library> getAll() {
        return repo.findAllSorted();
    }

    public Library getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Library save(Library library) {
        return repo.save(library);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Library::getId)
                .filter(id -> id != null && id.startsWith("LIB"))
                .map(id -> id.substring(3))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "LIB" + (maxNumber + 1);
    }

    public Library newForForm() {
        Library lib = new Library();
        lib.setId(generateNextId());
        return lib;
    }

    // Business validation helpers
    public boolean existsByName(String name) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCase(name.trim());
    }

    public boolean existsByNameForOtherLibrary(String name, String excludedId) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCaseAndIdNot(name.trim(), excludedId);
    }
}