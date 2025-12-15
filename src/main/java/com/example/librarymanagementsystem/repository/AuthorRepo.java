package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Author;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepo extends JpaRepository<Author, String> {

    //VALIDĂRI
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, String id);

    //SORTARE NATURALĂ
    @Query("SELECT a FROM Author a ORDER BY CAST(SUBSTRING(a.id, 2) AS int)")
    List<Author> findAllSorted();

    // ========================================
    //SORTARE DINAMICA
    // ========================================

    // Filtrare după nume
    List<Author> findByNameContainingIgnoreCase(String name, Sort sort);

    // Filtrare după naționalitate
    List<Author> findByNationalityContainingIgnoreCase(String nationality, Sort sort);

    // Filtrare după AMBELE
    List<Author> findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCase(
            String name, String nationality, Sort sort);
}