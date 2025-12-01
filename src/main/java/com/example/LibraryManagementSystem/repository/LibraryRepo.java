package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepo extends JpaRepository<Library, String> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, String id);

    // Sortare naturală folosind CAST în MySQL
    @Query("SELECT l FROM Library l ORDER BY CAST(SUBSTRING(l.id, 4) AS int)")
    List<Library> findAllSorted();
}