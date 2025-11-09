package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepo extends InFileRepository<BookDetails> {
    public BookRepo() {
        super("src/main/resources/data/book.json",
                new TypeReference<java.util.List<BookDetails>>() {});
    }
}
