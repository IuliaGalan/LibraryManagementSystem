package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.repository.LoanRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepo repo;

    public LoanService(LoanRepo repo) {
        this.repo = repo;
    }

    // LIST cu sortare naturală
    public List<Loan> getAll() {
        return repo.findAllSorted();
    }

    // GET BY ID
    public Loan getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE
    public Loan save(Loan loan) {
        return repo.save(loan);
    }

    // CREATE cu ID explicit
    public void add(String id, Loan loan) {
        loan.setId(id);
        repo.save(loan);
    }

    // UPDATE
    public void update(String id, Loan loan) {
        loan.setId(id);
        repo.save(loan);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // GENERATE NEXT ID
    public String generateNextId() {
        return "Loan" + (repo.count() + 1);
    }

    // FOR FORM
    public Loan newForForm() {
        Loan loan = new Loan();
        loan.setId(generateNextId());
        return loan;
    }
}