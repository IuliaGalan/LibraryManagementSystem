package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libraries")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    // ========================================
    // ✅ MODIFICI METODA LIST - AICI E SCHIMBAREA PRINCIPALĂ!
    // ========================================
    @GetMapping
    public String list(
            // Parametri pentru SORTARE
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,

            // Parametri pentru FILTRARE
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) String filterAddress,
            @RequestParam(required = false) String filterEmail,

            Model model) {

        // 1️⃣ Obține lista sortată și filtrată
        List<Library> libraries = service.getAll(sort, direction,
                filterName, filterAddress, filterEmail);

        // 2️⃣ Trimite datele către view
        model.addAttribute("libraries", libraries);

        // 3️⃣ Trimite parametrii actuali (pentru UI să știe ce e selectat)
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);

        // 4️⃣ Trimite filtrele înapoi (ca să rămână în formular)
        model.addAttribute("filterName", filterName);
        model.addAttribute("filterAddress", filterAddress);
        model.addAttribute("filterEmail", filterEmail);

        return "library/index";
    }

    // ========================================
    // ✅ RESTUL METODELOR RĂMÂN EXACT LA FEL
    // ========================================

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

        // 🔹 VALIDARE: Alt library are deja acest nume?
        if (library.getName() != null && !library.getName().isBlank()) {
            if (service.existsByNameForOtherLibrary(library.getName(), id)) {
                bindingResult.rejectValue("name", "error.library",
                        "This library name is already used by another library.");
            }
        }

        // Dacă sunt erori, rămâi pe edit
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