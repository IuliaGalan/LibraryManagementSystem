package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.service.LoanService;
import com.example.librarymanagementsystem.service.MemberService;
import com.example.librarymanagementsystem.service.ReadableItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;
    private final MemberService memberService;
    private final ReadableItemService itemService;

    public LoanController(LoanService service,
                          MemberService memberService,
                          ReadableItemService itemService) {
        this.service = service;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    // READ - toate împrumuturile
    @GetMapping
    public String getAll(Model model) {
        List<Loan> loans = service.getAll();
        model.addAttribute("loans", loans);
        return "loan/index";
    }

    // CREATE - afișează formularul
    @GetMapping("/new")
    public String form(Model model) {
        Loan loan = service.newForForm();
        List<Member> members = memberService.getAll();
        List<ReadableItem> items = itemService.getAll();

        model.addAttribute("loan", loan);
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "loan/form";
    }

    // CREATE - salvează împrumutul nou
    @PostMapping
    public String create(@ModelAttribute Loan loan) {
        service.save(loan);
        return "redirect:/loans";
    }

    // READ - detalii împrumut
    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loans";
        }
        model.addAttribute("loan", loan);
        return "loan/details";
    }

    // UPDATE - afișează formularul de editare
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Loan loan = service.getById(id);
        if (loan == null) {
            return "redirect:/loans";
        }

        List<Member> members = memberService.getAll();
        List<ReadableItem> items = itemService.getAll();

        model.addAttribute("loan", loan);
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "loan/form";
    }

    // UPDATE - salvează modificările
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Loan loan) {
        loan.setId(id);
        service.save(loan);
        return "redirect:/loans";
    }

    // DELETE - șterge împrumutul
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/loans";
    }

    // Filtrare împrumuturi după membru
    @GetMapping("/member/{memberId}")
    public String getByMember(@PathVariable String memberId, Model model) {
        List<Loan> loans = service.getLoansByMember(memberId);
        model.addAttribute("loans", loans);
        return "loan/index";
    }

    // Filtrare împrumuturi după status
    @GetMapping("/status/{status}")
    public String getByStatus(@PathVariable String status, Model model) {
        List<Loan> loans = service.getLoansByStatus(status);
        model.addAttribute("loans", loans);
        return "loan/index";
    }
}