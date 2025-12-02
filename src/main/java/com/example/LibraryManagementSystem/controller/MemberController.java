package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("members", service.getAll());
        return "member/index";
    }

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

        member.setId(id); // Sigur setăm ID-ul corect

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