package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepo extends JpaRepository<Library, String> {

    // Verifică dacă există o bibliotecă cu acest nume (pentru CREATE)
    boolean existsByNameIgnoreCase(String name);

    // Verifică dacă există altă bibliotecă cu acest nume (pentru UPDATE)
    boolean existsByNameIgnoreCaseAndIdNot(String name, String id);
}