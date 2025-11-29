package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookDetails, String> {

    // există deja o carte cu acest titlu? (pentru create)
    boolean existsByTitleIgnoreCase(String title);

    // există altă carte cu acest titlu? (pentru update)
    boolean existsByTitleIgnoreCaseAndIdNot(String title, String id);
}
