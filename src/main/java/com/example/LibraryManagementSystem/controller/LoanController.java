package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.service.LoanService;
import com.example.librarymanagementsystem.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;
    private final MemberService memberService;

    public LoanController(LoanService service, MemberService memberService) {
        this.service = service;
        this.memberService = memberService;
    }

    // ========================================
    // ✅ MODIFICI METODA LIST - AICI E SCHIMBAREA PRINCIPALĂ!
    // ========================================
    @GetMapping
    public String list(
            // Parametri pentru SORTARE
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,

            // Parametri pentru FILTRARE
            @RequestParam(required = false) String filterMemberName,
            @RequestParam(required = false) String filterStatus,
            @RequestParam(required = false) String filterLoanDate,

            Model model) {

        // 1️⃣ Obține lista sortată și filtrată
        List<Loan> loans = service.getAll(sort, direction,
                filterMemberName, filterStatus, filterLoanDate);

        // 2️⃣ Trimite datele către view
        model.addAttribute("loans", loans);

        // 3️⃣ Trimite parametrii actuali (pentru UI să știe ce e selectat)
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);

        // 4️⃣ Trimite filtrele înapoi (ca să rămână în formular)
        model.addAttribute("filterMemberName", filterMemberName);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterLoanDate", filterLoanDate);

        // 5️⃣ Trimite lista de status-uri pentru dropdown
        model.addAttribute("statuses", Loan.LoanStatus.values());

        return "loan/index";
    }

    // ========================================
    // ✅ RESTUL METODELOR RĂMÂN EXACT LA FEL
    // ========================================

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("loan", service.newForForm());
        model.addAttribute("members", memberService.getAll());
        model.addAttribute("statuses", Loan.LoanStatus.values());
        return "loan/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute Loan loan,
                         @RequestParam("memberId") String memberId) {
        loan.setMember(memberService.getById(memberId));
        service.add(loan.getId(), loan);
        return "redirect:/loans";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loans";
        }
        model.addAttribute("loan", loan);
        return "loan/details";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loans";
        }
        model.addAttribute("loan", loan);
        model.addAttribute("members", memberService.getAll());
        model.addAttribute("statuses", Loan.LoanStatus.values());
        return "loan/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Loan loan,
                         @RequestParam("memberId") String memberId) {
        loan.setId(id);
        loan.setMember(memberService.getById(memberId));
        service.update(id, loan);
        return "redirect:/loans";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/loans";
    }
}