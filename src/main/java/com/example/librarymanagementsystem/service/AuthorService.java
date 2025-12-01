package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepo repo;

    public AuthorService(AuthorRepo repo) {
        this.repo = repo;
    }

    // RETURNĂM AUTORII SORTAȚI NATURAL (A1, A2, .. A10)
    public List<Author> getAll() {
        return repo.findAllSorted();
    }

    public Author getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Author save(Author a) {
        return repo.save(a);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    // GENERARE ID: A1, A2, A3, ...
    public String generateNextId() {
        return "A" + (repo.count() + 1);
    }

    public Author newForForm() {
        Author a = new Author();
        a.setId(generateNextId());
        return a;
    }

    // --- VALIDĂRI ---

    public boolean existsByName(String name) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCase(name.trim());
    }

    public boolean existsByNameForOtherAuthor(String name, String excludedId) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCaseAndIdNot(name.trim(), excludedId);
    }
}
