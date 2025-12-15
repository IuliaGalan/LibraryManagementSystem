package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.LibraryService;
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
    private final LibraryService libraryService;

    public MemberController(MemberService service, LibraryService libraryService) {
        this.service = service;
        this.libraryService = libraryService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) String filterAddress,
            @RequestParam(required = false) String filterEmail,
            Model model) {

        List<Member> members = service.getAll(sort, direction, filterName, filterAddress, filterEmail);

        model.addAttribute("members", members);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterName", filterName);
        model.addAttribute("filterAddress", filterAddress);
        model.addAttribute("filterEmail", filterEmail);

        return "member/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("member", service.newForForm());
        model.addAttribute("libraries", libraryService.getAll());
        return "member/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("member") Member member,
                         BindingResult bindingResult,
                         @RequestParam(value = "libraryId", required = false) String libraryId,
                         Model model) {

        // Validare: Email duplicat
        if (member.getEmail() != null && !member.getEmail().isBlank()) {
            if (service.existsByEmail(member.getEmail())) {
                bindingResult.rejectValue("email", "error.member",
                        "This email already exists.");
            }
        }

        // Set library dacă e specificat
        if (libraryId != null && !libraryId.isBlank()) {
            member.setLibrary(libraryService.getById(libraryId));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("libraries", libraryService.getAll());
            return "member/form";
        }

        service.save(member);
        return "redirect:/members";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        return "member/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Member member = service.getById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        model.addAttribute("libraries", libraryService.getAll());
        return "member/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("member") Member member,
                         BindingResult bindingResult,
                         @RequestParam(value = "libraryId", required = false) String libraryId,
                         Model model) {

        member.setId(id);

        // Validare: Alt member are deja acest email
        if (member.getEmail() != null && !member.getEmail().isBlank()) {
            if (service.existsByEmailForOtherMember(member.getEmail(), id)) {
                bindingResult.rejectValue("email", "error.member",
                        "This email is already used by another member.");
            }
        }

        // Set library dacă e specificat
        if (libraryId != null && !libraryId.isBlank()) {
            member.setLibrary(libraryService.getById(libraryId));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("libraries", libraryService.getAll());
            return "member/edit";
        }

        service.save(member);
        return "redirect:/members";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/members";
    }
}