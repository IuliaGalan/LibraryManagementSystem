package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Author;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepo extends InFileRepository<Author> {
    public AuthorRepo() {
        super("src/main/resources/data/author.json",
                new TypeReference<java.util.List<Author>>() {});
    }
}
