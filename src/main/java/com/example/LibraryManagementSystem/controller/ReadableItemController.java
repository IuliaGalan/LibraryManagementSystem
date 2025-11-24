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

    // LIST
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("items", service.getAll());
        return "readableitem/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("item", new ReadableItem());
        model.addAttribute("statuses", ReadableItem.Status.values());
        return "readableitem/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute("item") ReadableItem item) {
        service.add(item.getId(), item);
        return "redirect:/readableitem";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/readableitem";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        ReadableItem item = service.getById(id);
        if (item == null) {
            return "redirect:/readableitem";
        }
        model.addAttribute("item", item);
        model.addAttribute("statuses", ReadableItem.Status.values());
        return "readableitem/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("item") ReadableItem item) {
        item.setId(id);
        service.update(id, item);
        return "redirect:/readableitem";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        ReadableItem item = service.getById(id);
        if (item == null) {
            return "redirect:/readableitem";
        }
        model.addAttribute("item", item);
        return "readableitem/details";
    }
}
