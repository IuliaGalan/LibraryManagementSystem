package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookAuthor;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class BookAuthorRepo extends InFileRepository<BookAuthor> {
    public BookAuthorRepo() {
        super("src/main/resources/data/bookAuthor.json",
                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<BookAuthor>>() {});
    }
}

