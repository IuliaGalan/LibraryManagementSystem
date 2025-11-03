package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.service.MagazineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService service;

    public MagazineController(MagazineService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("magazines", service.getAll());
        return "magazine/index"; // templates/magazine/index.html
    }

    // FORM (NEW)
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("magazine", new MagazineDetails());
        return "magazine/form"; // templates/magazine/form.html
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute MagazineDetails magazine) {
        service.add(magazine.getId(), magazine);
        return "redirect:/magazines";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/magazines";
    }
}
