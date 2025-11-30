package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    // READ - toți membrii
    @GetMapping
    public String getAll(Model model) {
        List<Member> members = service.getAll();
        model.addAttribute("members", members);
        return "member/index";
    }

    // CREATE - afișează formularul
    @GetMapping("/new")
    public String form(Model model) {
        Member member = service.newForForm();
        model.addAttribute("member", member);
        return "member/form";
    }

    // CREATE - salvează membrul nou
    @PostMapping
    public String create(@ModelAttribute Member member) {
        service.save(member);
        return "redirect:/members";
    }

    // READ - detalii membru
    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        return "member/details";
    }

    // UPDATE - afișează formularul de editare
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        return "member/form";
    }

    // UPDATE - salvează modificările
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Member member) {
        member.setId(id);
        service.save(member);
        return "redirect:/members";
    }

    // DELETE - șterge membrul
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/members";
    }
}