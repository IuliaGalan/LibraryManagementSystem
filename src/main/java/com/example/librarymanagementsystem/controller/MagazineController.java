package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.service.MagazineService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService service;

    public MagazineController(MagazineService service) {
        this.service = service;
    }

    // ✅ LIST
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterTitle,
            @RequestParam(required = false) String filterPublisher,
            @RequestParam(required = false) String filterLanguage,
            Model model) {

        List<MagazineDetails> magazines = service.getAll(sort, direction, filterTitle, filterPublisher, filterLanguage);

        model.addAttribute("magazines", magazines);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterTitle", filterTitle);
        model.addAttribute("filterPublisher", filterPublisher);
        model.addAttribute("filterLanguage", filterLanguage);

        return "magazine/index";
    }

    // ✅ CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("magazine", service.newForForm());
        return "magazine/form";
    }

    // ✅ CREATE - CU VALIDARE TITLE DUPLICAT
    @PostMapping
    public String create(@Valid @ModelAttribute("magazine") MagazineDetails m,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        // 🔹 VALIDARE: Titlu duplicat?
        if (m.getTitle() != null && !m.getTitle().isBlank()) {
            if (service.existsByTitle(m.getTitle())) {
                bindingResult.rejectValue("title", "error.magazine",
                        "This magazine title already exists.");
            }
        }

        if (bindingResult.hasErrors()) {
            return "magazine/form";
        }

        service.save(m);

        // ✅ MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Magazine '" + m.getTitle() + "' created successfully!");

        return "redirect:/magazines";
    }

    // ✅ DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        MagazineDetails magazine = service.getById(id);
        String magazineTitle = (magazine != null) ? magazine.getTitle() : "Magazine";

        service.delete(id);

        // ✅ MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Magazine '" + magazineTitle + "' deleted successfully!");

        return "redirect:/magazines";
    }

    // ✅ EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        MagazineDetails magazine = service.getById(id);
        if (magazine == null) return "redirect:/magazines";
        model.addAttribute("magazine", magazine);
        return "magazine/edit";
    }

    // ✅ UPDATE - CU VALIDARE TITLE DUPLICAT
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("magazine") MagazineDetails m,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        m.setId(id);

        // 🔹 VALIDARE: Altă revistă are deja acest titlu?
        if (m.getTitle() != null && !m.getTitle().isBlank()) {
            if (service.existsByTitleForOtherMagazine(m.getTitle(), id)) {
                bindingResult.rejectValue("title", "error.magazine",
                        "This magazine title is already used by another magazine.");
            }
        }

        if (bindingResult.hasErrors()) {
            return "magazine/edit";
        }

        service.save(m);

        // ✅ MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Magazine '" + m.getTitle() + "' updated successfully!");

        return "redirect:/magazines";
    }

    // ✅ DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        MagazineDetails m = service.getById(id);
        if (m == null) return "redirect:/magazines";

        model.addAttribute("magazine", m);
        model.addAttribute("author", m.getAuthor());

        return "magazine/details";
    }
}