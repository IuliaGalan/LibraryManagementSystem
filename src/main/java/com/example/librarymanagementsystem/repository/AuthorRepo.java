package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, String> {

    // pentru create: există deja un autor cu acest nume?
    boolean existsByNameIgnoreCase(String name);

    // pentru update: există alt autor cu acest nume (alt id)?
    boolean existsByNameIgnoreCaseAndIdNot(String name, String id);
}
