package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.service.LoanService;
import com.example.librarymanagementsystem.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterMemberName,
            @RequestParam(required = false) String filterStatus,
            @RequestParam(required = false) String filterLoanDate,
            Model model) {

        List<Loan> loans = service.getAll(sort, direction,
                filterMemberName, filterStatus, filterLoanDate);

        model.addAttribute("loans", loans);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterMemberName", filterMemberName);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterLoanDate", filterLoanDate);
        model.addAttribute("statuses", Loan.LoanStatus.values());

        return "loan/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("loan", service.newForForm());
        model.addAttribute("members", memberService.getAll());
        return "loan/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("loan") Loan loan,
                         BindingResult bindingResult,
                         @RequestParam("memberId") String memberId,
                         @RequestParam("statusInput") String statusInput,
                         Model model) {

        // Validare: Status valid?
        Loan.LoanStatus validStatus = null;
        try {
            validStatus = Loan.LoanStatus.valueOf(statusInput.toUpperCase().trim());
            loan.setStatus(validStatus);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("status", "error.loan",
                    "Status must be: OPEN, CLOSED, or OVERDUE");
        }

        // Validare: Due date după loan date?
        if (loan.getLoanDate() != null && loan.getDueDate() != null) {
            if (loan.getDueDate().isBefore(loan.getLoanDate())) {
                bindingResult.rejectValue("dueDate", "error.loan",
                        "Due date must be after loan date.");
            }
        }

        // Set member
        loan.setMember(memberService.getById(memberId));

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.getAll());
            return "loan/form";
        }

        service.save(loan);
        return "redirect:/loans";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loans";
        }
        model.addAttribute("loan", loan);
        return "loan/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loans";
        }
        model.addAttribute("loan", loan);
        model.addAttribute("members", memberService.getAll());
        return "loan/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("loan") Loan loan,
                         BindingResult bindingResult,
                         @RequestParam("memberId") String memberId,
                         @RequestParam("statusInput") String statusInput,
                         Model model) {

        loan.setId(id);

        // Validare: Status valid?
        Loan.LoanStatus validStatus = null;
        try {
            validStatus = Loan.LoanStatus.valueOf(statusInput.toUpperCase().trim());
            loan.setStatus(validStatus);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("status", "error.loan",
                    "Status must be: OPEN, CLOSED, or OVERDUE");
        }

        // Validare: Due date după loan date?
        if (loan.getLoanDate() != null && loan.getDueDate() != null) {
            if (loan.getDueDate().isBefore(loan.getLoanDate())) {
                bindingResult.rejectValue("dueDate", "error.loan",
                        "Due date must be after loan date.");
            }
        }

        // Set member
        loan.setMember(memberService.getById(memberId));

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.getAll());
            return "loan/edit";
        }

        service.save(loan);
        return "redirect:/loans";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/loans";
    }
}