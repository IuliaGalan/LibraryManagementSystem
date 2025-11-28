package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", service.getAll());
        return "author/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", service.newForForm());
        return "author/form";
    }

    @PostMapping
    public String create(@ModelAttribute Author a) {
        service.save(a);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Author author = service.getById(id);
        if (author == null) return "redirect:/authors";

        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Author a) {
        a.setId(id);
        service.save(a);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Author a = service.getById(id);
        if (a == null) return "redirect:/authors";

        model.addAttribute("author", a);
        return "author/details";
    }
}
