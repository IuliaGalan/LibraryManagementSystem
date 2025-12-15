package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Library;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepo extends JpaRepository<Library, String> {

    // ✅ SORTARE NATURALĂ - compatibilă cu toate bazele de date
    @Query("SELECT l FROM Library l ORDER BY " +
            "CASE WHEN LENGTH(l.id) = LENGTH('LIB') + 1 THEN 0 " +
            "WHEN LENGTH(l.id) = LENGTH('LIB') + 2 THEN 1 " +
            "WHEN LENGTH(l.id) = LENGTH('LIB') + 3 THEN 2 " +
            "ELSE 3 END, l.id")
    List<Library> findAllSorted();

    List<Library> findAll(Sort sort);

    List<Library> findByNameContainingIgnoreCase(String name, Sort sort);
    List<Library> findByAddressContainingIgnoreCase(String address, Sort sort);
    List<Library> findByEmailContainingIgnoreCase(String email, Sort sort);

    List<Library> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
            String name, String address, Sort sort);
    List<Library> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String email, Sort sort);
    List<Library> findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String address, String email, Sort sort);

    List<Library> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String address, String email, Sort sort);

    Library findByEmail(String email);
    Library findByName(String name);
}