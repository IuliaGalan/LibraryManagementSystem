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

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("members", service.getAll());
        return "member/index"; // templates/member/index.html
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("member", new Member());
        return "member/form"; // templates/member/form.html
    }

    @PostMapping
    public String create(@ModelAttribute Member member) {
        if (member.getId() == null || member.getId().isBlank()) {
            member.setId(UUID.randomUUID().toString());
        }
        service.add(member.getId(), member);
        return "redirect:/member";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/member";
    }
}
