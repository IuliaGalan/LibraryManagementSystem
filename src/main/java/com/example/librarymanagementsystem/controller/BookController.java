package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book/index"; // -> templates/book/index.html
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", new BookDetails());
        return "book/form"; // -> templates/book/form.html
    }

    @PostMapping
    public String create(@ModelAttribute BookDetails b) {
        bookService.add(b.getId(), b);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
