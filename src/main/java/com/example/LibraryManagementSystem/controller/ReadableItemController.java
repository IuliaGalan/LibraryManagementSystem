package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.service.ReadableItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/readableitem")
public class ReadableItemController {

    private final ReadableItemService service;

    public ReadableItemController(ReadableItemService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("items", service.getAll());
        return "readableitem/index"; // templates/readableitem/index.html
    }



    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("item", new ReadableItem());
        model.addAttribute("statuses", ReadableItem.Status.values());
        return "readableitem/form";
    }


    @PostMapping
    public String create(@ModelAttribute("item") ReadableItem item) {

        service.add(item.getId(), item);
        return "redirect:/readableitem";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/readableitem";
    }
}
