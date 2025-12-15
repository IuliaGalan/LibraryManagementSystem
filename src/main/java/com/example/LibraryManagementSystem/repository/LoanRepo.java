package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Loan;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepo extends JpaRepository<Loan, String> {

    // ✅ SORTARE NATURALĂ
    @Query("SELECT l FROM Loan l ORDER BY CAST(SUBSTRING(l.id, 5) AS int)")
    List<Loan> findAllSorted();

    // ✅ SORTARE DINAMICĂ
    List<Loan> findAll(Sort sort);

    // ✅ FILTRARE - 1 filtru
    List<Loan> findByMember_NameContainingIgnoreCase(String memberName, Sort sort);
    List<Loan> findByStatus(Loan.LoanStatus status, Sort sort);
    List<Loan> findByLoanDate(LocalDate loanDate, Sort sort);

    // ✅ FILTRARE - 2 filtre
    List<Loan> findByMember_NameContainingIgnoreCaseAndStatus(
            String memberName, Loan.LoanStatus status, Sort sort);
    List<Loan> findByMember_NameContainingIgnoreCaseAndLoanDate(
            String memberName, LocalDate loanDate, Sort sort);
    List<Loan> findByStatusAndLoanDate(
            Loan.LoanStatus status, LocalDate loanDate, Sort sort);

    // ✅ FILTRARE - toate 3 filtre
    List<Loan> findByMember_NameContainingIgnoreCaseAndStatusAndLoanDate(
            String memberName, Loan.LoanStatus status, LocalDate loanDate, Sort sort);
}