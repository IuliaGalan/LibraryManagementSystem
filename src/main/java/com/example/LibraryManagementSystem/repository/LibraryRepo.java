package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Library;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class LibraryRepo extends InFileRepository<Library> {
    public LibraryRepo() {
        super("src/main/resources/data/library.json",
                new TypeReference<java.util.List<Library>>() {
                });
    }
}