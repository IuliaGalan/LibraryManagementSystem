package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.service.MagazineService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService service;

    public MagazineController(MagazineService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("magazines", service.getAll());
        return "magazine/index";
    }

    // FORM CREATE
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("magazine", service.newForForm());
        return "magazine/form";
    }

    // CREATE
    @PostMapping
    public String create(@Valid @ModelAttribute("magazine") MagazineDetails m,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            // rămânem pe formular și afișăm erorile
            return "magazine/form";
        }

        service.save(m);
        return "redirect:/magazines";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/magazines";
    }

    // FORM EDIT
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        MagazineDetails magazine = service.getById(id);
        if (magazine == null) return "redirect:/magazines";

        model.addAttribute("magazine", magazine);
        return "magazine/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("magazine") MagazineDetails m,
                         BindingResult bindingResult,
                         Model model) {

        m.setId(id); // siguranță: ID-ul vine din URL

        if (bindingResult.hasErrors()) {
            // rămânem pe formularul de edit cu erori
            return "magazine/edit";
        }

        service.save(m);
        return "redirect:/magazines";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        MagazineDetails m = service.getById(id);
        if (m == null) return "redirect:/magazines";

        model.addAttribute("magazine", m);
        model.addAttribute("author", m.getAuthor()); // 👈 aici trimitem și autorul (poate fi null)

        return "magazine/details";
    }
}
