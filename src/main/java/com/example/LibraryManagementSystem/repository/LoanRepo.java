package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepo extends JpaRepository<Loan, String> {

    // Găsește toate împrumuturile unui membru
    List<Loan> findByMember_Id(String memberId);

    // Găsește toate împrumuturile cu un anumit status
    List<Loan> findByStatus(String status);

    // Găsește împrumuturile unui membru cu un anumit status
    List<Loan> findByMember_IdAndStatus(String memberId, String status);
}