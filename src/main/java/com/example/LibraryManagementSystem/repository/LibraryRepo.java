package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Library;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepo extends JpaRepository<Library, String> {

    // ✅ VALIDĂRI (deja le ai)
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, String id);

    // ✅ SORTARE NATURALĂ (o păstrezi pentru compatibilitate)
    @Query("SELECT l FROM Library l ORDER BY CAST(SUBSTRING(l.id, 4) AS int)")
    List<Library> findAllSorted();

    // ========================================
    // ✅ SORTARE DINAMICĂ
    // ========================================
    List<Library> findAll(Sort sort);

    // ========================================
    // ✅ FILTRARE (queries noi)
    // ========================================

    // Filtrare după nume (conține text, ignore case)
    List<Library> findByNameContainingIgnoreCase(String name, Sort sort);

    // Filtrare după adresă (conține text, ignore case)
    List<Library> findByAddressContainingIgnoreCase(String address, Sort sort);

    // Filtrare după email (conține text, ignore case)
    List<Library> findByEmailContainingIgnoreCase(String email, Sort sort);

    // Filtrare după nume ȘI adresă
    List<Library> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
            String name, String address, Sort sort);

    // Filtrare după nume ȘI email
    List<Library> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String email, Sort sort);

    // Filtrare după adresă ȘI email
    List<Library> findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String address, String email, Sort sort);

    // Filtrare după TOATE TREI
    List<Library> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String address, String email, Sort sort);
}