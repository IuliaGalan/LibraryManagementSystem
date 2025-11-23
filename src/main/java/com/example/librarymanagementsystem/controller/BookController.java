package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService bookService,
                          BookAuthorService bookAuthorService) {
        this.bookService = bookService;
        this.bookAuthorService = bookAuthorService;
    }

    // LIST – afișează toate cărțile
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", bookService.newForForm());
        return "book/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute("book") BookDetails b) {
        bookService.add(b.getId(), b);
        return "redirect:/books";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        BookDetails book = bookService.getById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "book/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("book") BookDetails book) {
        book.setId(id);
        bookService.update(id, book);
        return "redirect:/books";
    }

    // DETAILS – carte + lista autorilor ei
    @GetMapping("/{id}/details")
    public String showDetails(@PathVariable String id, Model model) {
        BookDetails book = bookService.getById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);

        // 👇 luăm toți autorii acestei cărți prin BookAuthorService
        List<Author> authors = bookAuthorService.getAuthorsForBook(id);
        model.addAttribute("authors", authors);

        return "book/details";
    }
}
