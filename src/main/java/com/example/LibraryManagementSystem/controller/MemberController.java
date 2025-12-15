package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
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
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) String filterAddress,
            @RequestParam(required = false) String filterEmail,

            Model model) {

        // 1️⃣ Obține lista sortată și filtrată
        List<Member> members = service.getAll(sort, direction,
                filterName, filterAddress, filterEmail);

        // 2️⃣ Trimite datele către view
        model.addAttribute("members", members);

        // 3️⃣ Trimite parametrii actuali (pentru UI să știe ce e selectat)
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);

        // 4️⃣ Trimite filtrele înapoi (ca să rămână în formular)
        model.addAttribute("filterName", filterName);
        model.addAttribute("filterAddress", filterAddress);
        model.addAttribute("filterEmail", filterEmail);

        return "member/index";
    }

    // ========================================
    // ✅ RESTUL METODELOR RĂMÂN EXACT LA FEL
    // ========================================

    // CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("member", service.newForForm());
        return "member/form";
    }

    // CREATE - CU VALIDARE EMAIL DUPLICAT ✅
    @PostMapping
    public String create(@Valid @ModelAttribute Member member,
                         BindingResult bindingResult,
                         Model model) {

        // 🔹 VALIDARE: Email duplicat?
        if (member.getEmail() != null && !member.getEmail().isBlank()) {
            if (service.existsByEmail(member.getEmail())) {
                bindingResult.rejectValue("email", "error.member",
                        "This email is already registered.");
            }
        }

        // Dacă sunt erori, rămâi pe formular
        if (bindingResult.hasErrors()) {
            return "member/form";
        }

        service.save(member);
        return "redirect:/members";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        return "member/details";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        return "member/edit";
    }

    // UPDATE - CU VALIDARE EMAIL DUPLICAT ✅
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute Member member,
                         BindingResult bindingResult,
                         Model model) {

        member.setId(id);

        // 🔹 VALIDARE: Alt membru are deja acest email?
        if (member.getEmail() != null && !member.getEmail().isBlank()) {
            if (service.existsByEmailForOtherMember(member.getEmail(), id)) {
                bindingResult.rejectValue("email", "error.member",
                        "This email is already registered to another member.");
            }
        }

        // Dacă sunt erori, rămâi pe edit
        if (bindingResult.hasErrors()) {
            return "member/edit";
        }

        service.save(member);
        return "redirect:/members";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/members";
    }
}