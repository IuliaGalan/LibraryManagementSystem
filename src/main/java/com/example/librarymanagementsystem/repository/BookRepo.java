package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookDetails;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<BookDetails, String> {

    //VALIDĂRI
    boolean existsByTitleIgnoreCase(String title);
    boolean existsByTitleIgnoreCaseAndIdNot(String title, String id);

    //SORTARE NATURALĂ
    @Query("SELECT b FROM BookDetails b ORDER BY CAST(SUBSTRING(b.id, 2) AS int)")
    List<BookDetails> findAllSorted();


    //SORTARE DINAMICĂ (NOU)
    List<BookDetails> findAll(Sort sort);

    // Filtrare după titlu
    List<BookDetails> findByTitleContainingIgnoreCase(String title, Sort sort);

    // Filtrare după gen
    List<BookDetails> findByGenreContainingIgnoreCase(String genre, Sort sort);

    // Filtrare după AMBELE
    List<BookDetails> findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(
            String title, String genre, Sort sort);
}