package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.repository.LoanRepo;

public class LoanService extends BaseService<Loan> {

    public LoanService(LoanRepo repo) {
        super(repo);
    }
}
