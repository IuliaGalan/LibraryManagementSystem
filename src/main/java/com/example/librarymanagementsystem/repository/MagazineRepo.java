package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.MagazineDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepo extends JpaRepository<MagazineDetails, String> {

    // există deja o revistă cu acest titlu? (create)
    boolean existsByTitleIgnoreCase(String title);

    // există altă revistă cu același titlu? (update)
    boolean existsByTitleIgnoreCaseAndIdNot(String title, String id);

    // <<< sortare naturală după partea numerică din ID ("M1", "M2", ..., "M10")
    @Query("SELECT m FROM MagazineDetails m ORDER BY CAST(SUBSTRING(m.id, 2) AS int)")
    List<MagazineDetails> findAllSorted();
}
