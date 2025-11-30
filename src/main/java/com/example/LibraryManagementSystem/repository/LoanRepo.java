package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepo extends JpaRepository<Loan, String> {

    // Sortare naturală: Loan1, Loan2, Loan3, ..., Loan10, Loan11
    @Query("SELECT l FROM Loan l ORDER BY CAST(SUBSTRING(l.id, 5) AS int)")
    List<Loan> findAllSorted();
}