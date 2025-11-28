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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", service.getAll());
        return "book/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", service.newForForm());
        return "book/form";
    }

    @PostMapping
    public String create(@ModelAttribute BookDetails b) {
        service.save(b);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        BookDetails book = service.getById(id);
        if (book == null) return "redirect:/books";

        model.addAttribute("book", book);
        return "book/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute BookDetails book) {
        book.setId(id);
        service.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        BookDetails b = service.getById(id);
        if (b == null) return "redirect:/books";

        model.addAttribute("book", b);
        return "book/details";
    }
}
