package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.BookAuthorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService service, BookAuthorService bookAuthorService) {
        this.service = service;
        this.bookAuthorService = bookAuthorService;
    }

    // GET ALL
    @GetMapping
    public String getAll(Model model) {
        var books = service.getAll();
        // Map: bookId -> listă autori
        Map<String, List<Author>> authorsByBook = new LinkedHashMap<>();
        for (var b : books) {
            authorsByBook.put(b.getId(), bookAuthorService.getAuthorsOfBook(b.getId()));
        }
        model.addAttribute("books", books);
        model.addAttribute("authorsByBook", authorsByBook);
        return "book/index";
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
