package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("libraries", service.getAll());
        return "library/index"; // templates/library/index.html
    }


    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("library", new Library());
        return "library/form"; // templates/library/form.html
    }


    @PostMapping
    public String create(@ModelAttribute Library library) {
        service.add(library.getId(), library);
        return "redirect:/library";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/library";
    }
}
