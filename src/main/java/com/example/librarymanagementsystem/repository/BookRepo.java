package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<BookDetails, String> {

    // există deja o carte cu acest titlu? (pentru create)
    boolean existsByTitleIgnoreCase(String title);

    // există altă carte cu acest titlu? (pentru update)
    boolean existsByTitleIgnoreCaseAndIdNot(String title, String id);

    // <<< Sortare naturală după partea numerică din ID ("B1", "B2", ..., "B10")
    @Query("SELECT b FROM BookDetails b ORDER BY CAST(SUBSTRING(b.id, 2) AS int)")
    List<BookDetails> findAllSorted();
}
