package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {

    // Verifică dacă există un membru cu acest email
    boolean existsByEmailIgnoreCase(String email);

    // Verifică dacă există alt membru cu acest email
    boolean existsByEmailIgnoreCaseAndIdNot(String email, String id);
}