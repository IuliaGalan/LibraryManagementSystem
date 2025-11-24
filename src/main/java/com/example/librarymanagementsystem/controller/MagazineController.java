package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.service.MagazineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService magazineService;

    public MagazineController(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    // LIST – afișează toate revistele
    @GetMapping
    public String listMagazines(Model model) {
        model.addAttribute("magazines", magazineService.getAll());
        return "magazine/index";
    }

    // CREATE FORM – revistă nouă, cu ID generat automat
    @GetMapping("/new")
    public String newMagazineForm(Model model) {
        model.addAttribute("magazine", magazineService.newForForm());
        return "magazine/form";
    }

    // CREATE – salvează revistă nouă
    @PostMapping
    public String createMagazine(@ModelAttribute("magazine") MagazineDetails m) {
        magazineService.add(m.getId(), m);
        return "redirect:/magazines";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteMagazine(@PathVariable String id) {
        magazineService.delete(id);
        return "redirect:/magazines";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editMagazineForm(@PathVariable String id, Model model) {
        MagazineDetails magazine = magazineService.getById(id);
        if (magazine == null) {
            return "redirect:/magazines";
        }
        model.addAttribute("magazine", magazine);
        return "magazine/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String updateMagazine(@PathVariable String id,
                                 @ModelAttribute("magazine") MagazineDetails magazine) {
        magazine.setId(id);
        magazineService.update(id, magazine);
        return "redirect:/magazines";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String magazineDetails(@PathVariable String id, Model model) {
        MagazineDetails magazine = magazineService.getById(id);
        if (magazine == null) {
            return "redirect:/magazines";
        }
        model.addAttribute("magazine", magazine);
        return "magazine/details";
    }
}
