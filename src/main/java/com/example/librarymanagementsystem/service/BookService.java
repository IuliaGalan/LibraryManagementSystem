package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.RepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookService extends BaseService<BookDetails> {

    public BookService(RepositoryInterface<BookDetails> repo) {
        super(repo);
    }

    // Generează următorul ID: B11, B12 etc.
    public String generateNextId() {
        int next = repo.findAll().stream()
                .map(BookDetails::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.startsWith("B"))
                .map(id -> id.substring(1))
                .filter(s -> s.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;
        return "B" + next;
    }

    // Creează un BookDetails gol, cu ID precompletat
    public BookDetails newForForm() {
        BookDetails book = new BookDetails();
        book.setId(generateNextId());
        return book;
    }
}
