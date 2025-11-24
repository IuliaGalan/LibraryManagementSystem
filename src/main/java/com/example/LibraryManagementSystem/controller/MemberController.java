package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("members", service.getAll());
        return "member/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("member", new Member());
        return "member/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute Member member) {
        if (member.getId() == null || member.getId().isBlank()) {
            member.setId(UUID.randomUUID().toString());
        }
        service.add(member.getId(), member);
        return "redirect:/member";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/member";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/member";
        }
        model.addAttribute("member", member);
        return "member/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Member member) {
        member.setId(id);
        service.update(id, member);
        return "redirect:/member";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/member";
        }
        model.addAttribute("member", member);
        return "member/details";
    }
}
