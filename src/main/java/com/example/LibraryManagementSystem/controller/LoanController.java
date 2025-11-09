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


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("loans", service.getAll());
        return "loan/index"; // templates/loan/index.html
    }


    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("loan", new Loan(null, null, null)); // pentru binding corect
        return "loan/form"; // templates/loan/form.html
    }


    @PostMapping
    public String create(@ModelAttribute Loan loan) {
        service.add(loan.getId(), loan);
        return "redirect:/loan";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/loan";
    }
}
