package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Loan;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRepo extends InFileRepository<Loan> {
    public LoanRepo() {
        super("src/main/resources/data/loan.json",
                new TypeReference<java.util.List<Loan>>() {});
    }
}
