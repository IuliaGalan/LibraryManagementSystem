package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.MagazineDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepo extends JpaRepository<MagazineDetails, String> {
}
