package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.MagazineDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepo extends JpaRepository<MagazineDetails, String> {

    // există deja o revistă cu acest titlu? (create)
    boolean existsByTitleIgnoreCase(String title);

    // există altă revistă cu același titlu? (update)
    boolean existsByTitleIgnoreCaseAndIdNot(String title, String id);
}
