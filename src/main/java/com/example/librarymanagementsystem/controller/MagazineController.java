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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("magazines", service.getAll());
        return "magazine/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("magazine", service.newForForm());
        return "magazine/form";
    }

    @PostMapping
    public String create(@ModelAttribute MagazineDetails m) {
        service.save(m);
        return "redirect:/magazines";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/magazines";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        MagazineDetails magazine = service.getById(id);
        if (magazine == null) return "redirect:/magazines";

        model.addAttribute("magazine", magazine);
        return "magazine/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute MagazineDetails m) {
        m.setId(id);
        service.save(m);
        return "redirect:/magazines";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        MagazineDetails m = service.getById(id);
        if (m == null) return "redirect:/magazines";

        model.addAttribute("magazine", m);
        return "magazine/details";
    }
}
