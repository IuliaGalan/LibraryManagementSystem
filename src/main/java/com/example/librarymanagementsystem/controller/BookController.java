package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("books", service.getAll());
        return "book/index"; // templates/book/index.html
    }

    // FORM (NEW)
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", new BookDetails());
        return "book/form"; // templates/book/form.html
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute BookDetails book) {
        service.add(book.getId(), book);
        return "redirect:/books";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/books";
    }
}
