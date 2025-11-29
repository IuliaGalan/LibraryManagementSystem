package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libraries")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    // READ - toate bibliotecile
    @GetMapping
    public String getAll(Model model) {
        List<Library> libraries = service.getAll();
        model.addAttribute("libraries", libraries);
        return "library/list";
    }

    // CREATE - afișează formularul
    @GetMapping("/new")
    public String form(Model model) {
        Library library = service.newForForm();
        model.addAttribute("library", library);
        return "library/form";
    }

    // CREATE - salvează biblioteca nouă
    @PostMapping
    public String create(@ModelAttribute Library library) {
        service.save(library);
        return "redirect:/libraries";
    }

    // READ - detalii bibliotecă
    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Library library = service.getById(id);
        if (library == null) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library);
        return "library/details";
    }

    // UPDATE - afișează formularul de editare
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Library library = service.getById(id);
        if (library == null) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library);
        return "library/form";
    }

    // UPDATE - salvează modificările
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Library library) {
        library.setId(id);
        service.save(library);
        return "redirect:/libraries";
    }

    // DELETE - șterge biblioteca
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/libraries";
    }
}