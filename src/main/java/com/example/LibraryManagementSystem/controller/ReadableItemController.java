package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.ReadableItemService;
import com.example.librarymanagementsystem.service.PublicationService;
import com.example.librarymanagementsystem.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ReadableItemController {

    private final ReadableItemService service;
    private final PublicationService publicationService;
    private final LibraryService libraryService;

    public ReadableItemController(ReadableItemService service,
                                  PublicationService publicationService,
                                  LibraryService libraryService) {
        this.service = service;
        this.publicationService = publicationService;
        this.libraryService = libraryService;
    }

    // READ - toate itemele
    @GetMapping
    public String getAll(Model model) {
        List<ReadableItem> items = service.getAll();
        model.addAttribute("items", items);
        return "item/index";
    }

    // CREATE - afișează formularul
    @GetMapping("/new")
    public String form(Model model) {
        ReadableItem item = service.newForForm();
        List<Publication> publications = publicationService.getAll();
        List<Library> libraries = libraryService.getAll();

        model.addAttribute("item", item);
        model.addAttribute("publications", publications);
        model.addAttribute("libraries", libraries);
        model.addAttribute("statuses", ReadableItem.ItemStatus.values());
        return "item/form";
    }

    // CREATE - salvează itemul nou
    @PostMapping
    public String create(@ModelAttribute ReadableItem item) {
        service.save(item);
        return "redirect:/items";
    }

    // READ - detalii item
    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        ReadableItem item = service.getById(id);
        if (item == null) {
            return "redirect:/items";
        }
        model.addAttribute("item", item);
        return "item/details";
    }

    // UPDATE - afișează formularul de editare
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        ReadableItem item = service.getById(id);
        if (item == null) {
            return "redirect:/items";
        }

        List<Publication> publications = publicationService.getAll();
        List<Library> libraries = libraryService.getAll();

        model.addAttribute("item", item);
        model.addAttribute("publications", publications);
        model.addAttribute("libraries", libraries);
        model.addAttribute("statuses", ReadableItem.ItemStatus.values());
        return "item/form";
    }

    // UPDATE - salvează modificările
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute ReadableItem item) {
        item.setId(id);
        service.save(item);
        return "redirect:/items";
    }

    // DELETE - șterge itemul
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/items";
    }

    // Filtrare iteme după bibliotecă
    @GetMapping("/library/{libraryId}")
    public String getByLibrary(@PathVariable String libraryId, Model model) {
        List<ReadableItem> items = service.getItemsByLibrary(libraryId);
        model.addAttribute("items", items);
        return "item/list";
    }

    // Filtrare iteme după publicație
    @GetMapping("/publication/{publicationId}")
    public String getByPublication(@PathVariable String publicationId, Model model) {
        List<ReadableItem> items = service.getItemsByPublication(publicationId);
        model.addAttribute("items", items);
        return "item/list";
    }

    // Filtrare iteme după status
    @GetMapping("/status/{status}")
    public String getByStatus(@PathVariable ReadableItem.ItemStatus status, Model model) {
        List<ReadableItem> items = service.getItemsByStatus(status);
        model.addAttribute("items", items);
        return "item/list";
    }
}