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

    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) String filterAddress,
            @RequestParam(required = false) String filterEmail,
            Model model) {

        List<Library> libraries = service.getAll(sort, direction, filterName, filterAddress, filterEmail);

        model.addAttribute("libraries", libraries);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterName", filterName);
        model.addAttribute("filterAddress", filterAddress);
        model.addAttribute("filterEmail", filterEmail);

        return "library/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("library", service.newForForm());
        return "library/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("library") Library library,
                         BindingResult bindingResult,
                         Model model) {

        // Validare: Email duplicat
        if (library.getEmail() != null && !library.getEmail().isBlank()) {
            if (service.existsByEmail(library.getEmail())) {
                bindingResult.rejectValue("email", "error.library",
                        "This email already exists.");
            }
        }

        // Validare: Nume duplicat
        if (library.getName() != null && !library.getName().isBlank()) {
            if (service.existsByName(library.getName())) {
                bindingResult.rejectValue("name", "error.library",
                        "This library name already exists.");
            }
        }

        if (bindingResult.hasErrors()) {
            return "library/form";
        }

        service.save(library);
        return "redirect:/libraries";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Library library = service.getById(id);
        if (library == null) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library);
        return "library/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Library library = service.getById(id);
        if (library == null) {
            return "redirect:/libraries";
        }
        model.addAttribute("library", library);
        return "library/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("library") Library library,
                         BindingResult bindingResult,
                         Model model) {

        library.setId(id);

        // Validare: Alt library are deja acest email
        if (library.getEmail() != null && !library.getEmail().isBlank()) {
            if (service.existsByEmailForOtherLibrary(library.getEmail(), id)) {
                bindingResult.rejectValue("email", "error.library",
                        "This email is already used by another library.");
            }
        }

        // Validare: Alt library are deja acest nume
        if (library.getName() != null && !library.getName().isBlank()) {
            if (service.existsByNameForOtherLibrary(library.getName(), id)) {
                bindingResult.rejectValue("name", "error.library",
                        "This library name is already used.");
            }
        }

        if (bindingResult.hasErrors()) {
            return "library/edit";
        }

        service.save(library);
        return "redirect:/libraries";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/libraries";
    }
}