package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loan")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("loans", service.getAll());
        return "loan/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("loan", new Loan(null, null, null));
        return "loan/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute Loan loan) {
        service.add(loan.getId(), loan);
        return "redirect:/loan";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/loan";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loan";
        }
        model.addAttribute("loan", loan);
        return "loan/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Loan loan) {
        loan.setId(id);
        service.update(id, loan);
        return "redirect:/loan";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loan";
        }
        model.addAttribute("loan", loan);
        return "loan/details";
    }
}
