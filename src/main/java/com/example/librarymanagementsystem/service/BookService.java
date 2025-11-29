package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepo repo;

    public BookService(BookRepo repo) {
        this.repo = repo;
    }

    public List<BookDetails> getAll() { return repo.findAll(); }

    public BookDetails getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public BookDetails save(BookDetails book) {
        return repo.save(book);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        return "B" + (repo.count() + 1);
    }

    public BookDetails newForForm() {
        BookDetails book = new BookDetails();
        book.setId(generateNextId());
        return book;
    }

    // --- Business validation helpers ---

    public boolean existsByTitle(String title) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCase(title.trim());
    }

    public boolean existsByTitleForOtherBook(String title, String excludedId) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCaseAndIdNot(title.trim(), excludedId);
    }
}
