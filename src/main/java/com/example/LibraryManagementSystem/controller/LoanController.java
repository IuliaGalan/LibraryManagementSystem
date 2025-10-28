package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    // test simplu
    @GetMapping("/hello")
    public String sayHello() {
        return "LoanController works!";
    }

    // toate împrumuturile
    @GetMapping
    public List<Loan> getAllLoans() {
        return service.getAll();
    }

    // adaugă un împrumut nou
    @PostMapping
    public void addLoan(@RequestBody Loan loan) {
        service.add(loan.getId(), loan);
    }

    // găsește un împrumut după id
    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable String id) {
        return service.getById(id);
    }

    // șterge un împrumut
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable String id) {
        service.delete(id);
    }
}
