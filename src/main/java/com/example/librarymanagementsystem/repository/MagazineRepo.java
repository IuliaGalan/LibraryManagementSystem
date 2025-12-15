package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.MagazineDetails;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepo extends JpaRepository<MagazineDetails, String> {

    //VALIDĂRI
    boolean existsByTitleIgnoreCase(String title);
    boolean existsByTitleIgnoreCaseAndIdNot(String title, String id);

    //SORTARE NATURALĂ
    @Query("SELECT m FROM MagazineDetails m ORDER BY CAST(SUBSTRING(m.id, 2) AS int)")
    List<MagazineDetails> findAllSorted();

// SORTARE DINAMICĂ
    List<MagazineDetails> findAll(Sort sort);

    // Filtrare după titlu
    List<MagazineDetails> findByTitleContainingIgnoreCase(String title, Sort sort);

    // Filtrare după publisher
    List<MagazineDetails> findByPublisherContainingIgnoreCase(String publisher, Sort sort);

    // Filtrare după limbă
    List<MagazineDetails> findByLanguageContainingIgnoreCase(String language, Sort sort);

    // Filtrare după titlu + publisher
    List<MagazineDetails> findByTitleContainingIgnoreCaseAndPublisherContainingIgnoreCase(
            String title, String publisher, Sort sort);

    // Filtrare după titlu + language
    List<MagazineDetails> findByTitleContainingIgnoreCaseAndLanguageContainingIgnoreCase(
            String title, String language, Sort sort);

    // Filtrare după publisher + language
    List<MagazineDetails> findByPublisherContainingIgnoreCaseAndLanguageContainingIgnoreCase(
            String publisher, String language, Sort sort);

    // Filtrare după toate 3
    List<MagazineDetails> findByTitleContainingIgnoreCaseAndPublisherContainingIgnoreCaseAndLanguageContainingIgnoreCase(
            String title, String publisher, String language, Sort sort);
}