package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    // CREATE - CU VALIDARE NAME DUPLICAT ✅
    @PostMapping
    public String create(@Valid @ModelAttribute Library library,
                         BindingResult bindingResult,
                         Model model) {

        // 🔹 VALIDARE: Nume duplicat?
        if (library.getName() != null && !library.getName().isBlank()) {
            if (service.existsByName(library.getName())) {
                bindingResult.rejectValue("name", "error.library",
                        "This library name already exists.");
            }
        }

        // Dacă sunt erori, rămâi pe formular
        if (bindingResult.hasErrors()) {
            return "library/form";
        }

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

    // UPDATE - CU VALIDARE NAME DUPLICAT ✅
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute Library library,
                         BindingResult bindingResult,
                         Model model) {

        library.setId(id);

        // 🔹validare, are bibliotecă are deja acest nume?
        if (library.getName() != null && !library.getName().isBlank()) {
            if (service.existsByNameForOtherLibrary(library.getName(), id)) {
                bindingResult.rejectValue("name", "error.library",
                        "This library name is already used by another library.");
            }
        }

        // Dacă sunt erori, raman pe edit
        if (bindingResult.hasErrors()) {
            return "library/edit";
        }

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