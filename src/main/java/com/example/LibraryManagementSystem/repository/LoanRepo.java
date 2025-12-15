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

    @Query("SELECT l FROM Loan l ORDER BY CAST(SUBSTRING(l.id, 5, 3) AS int)")
    List<Loan> findAllSorted();

    List<Loan> findAll(Sort sort);

    List<Loan> findByMember_NameContainingIgnoreCase(String memberName, Sort sort);
    List<Loan> findByStatus(Loan.LoanStatus status, Sort sort);
    List<Loan> findByLoanDate(LocalDate loanDate, Sort sort);

    List<Loan> findByMember_NameContainingIgnoreCaseAndStatus(
            String memberName, Loan.LoanStatus status, Sort sort);
    List<Loan> findByMember_NameContainingIgnoreCaseAndLoanDate(
            String memberName, LocalDate loanDate, Sort sort);
    List<Loan> findByStatusAndLoanDate(
            Loan.LoanStatus status, LocalDate loanDate, Sort sort);

    List<Loan> findByMember_NameContainingIgnoreCaseAndStatusAndLoanDate(
            String memberName, Loan.LoanStatus status, LocalDate loanDate, Sort sort);
}