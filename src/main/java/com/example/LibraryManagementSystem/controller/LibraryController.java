package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/libraries")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("libraries", service.getAll());
        return "library/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("library", service.newForForm());
        return "library/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute Library library) {
        service.save(library);
        return "redirect:/libraries";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Library library = service.getById(id);
        if (library == null) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library);
        return "library/details";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Library library = service.getById(id);
        if (library == null) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library);
        return "library/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Library library) {
        library.setId(id);
        service.save(library);
        return "redirect:/libraries";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/libraries";
    }
}