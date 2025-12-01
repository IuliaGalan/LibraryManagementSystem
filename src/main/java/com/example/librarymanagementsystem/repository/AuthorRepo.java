package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author, String> {

    // pentru create: există deja un autor cu acest nume?
    boolean existsByNameIgnoreCase(String name);

    // pentru update: există alt autor cu acest nume (alt id)?
    boolean existsByNameIgnoreCaseAndIdNot(String name, String id);

    // >>> SORTARE NATURALĂ după partea numerică din id ("A1", "A2", ..., "A10")
    @Query("SELECT a FROM Author a ORDER BY CAST(SUBSTRING(a.id, 2) AS int)")
    List<Author> findAllSorted();
}
