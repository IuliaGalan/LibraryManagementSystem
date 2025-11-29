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

    // READ - toate împrumuturile
    public List<Loan> getAll() {
        return repo.findAll();
    }

    // READ - un împrumut după ID
    public Loan getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE & UPDATE
    public Loan save(Loan loan) {
        return repo.save(loan);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // Generare ID automat
    public String generateNextId() {
        return "LOAN" + (repo.count() + 1);
    }

    // Helper pentru formular nou
    public Loan newForForm() {
        Loan loan = new Loan();
        loan.setId(generateNextId());
        return loan;
    }

    // --- Metode pentru relații ---

    // Găsește toate împrumuturile unui membru
    public List<Loan> getLoansByMember(String memberId) {
        return repo.findByMember_Id(memberId);
    }

    // Găsește împrumuturile după status
    public List<Loan> getLoansByStatus(String status) {
        return repo.findByStatus(status);
    }

    // Găsește împrumuturile unui membru cu un anumit status
    public List<Loan> getLoansByMemberAndStatus(String memberId, String status) {
        return repo.findByMember_IdAndStatus(memberId, status);
    }
}