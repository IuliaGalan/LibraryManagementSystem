package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;
    private final BookAuthorService bookAuthorService;

    public AuthorController(AuthorService service, BookAuthorService bookAuthorService) {
        this.service = service;
        this.bookAuthorService = bookAuthorService;
    }

    // LIST + booksByAuthor (UNICA rută GET /authors)
    @GetMapping
    public String index(Model model) {
        var authors = service.getAll();
        Map<String, List<BookDetails>> booksByAuthor = new LinkedHashMap<>();
        for (var a : authors) {
            booksByAuthor.put(a.getId(), bookAuthorService.getBooksOfAuthor(a.getId()));
        }
        model.addAttribute("authors", authors);
        model.addAttribute("booksByAuthor", booksByAuthor);
        return "author/index";
    }

    // FORM (GET /authors/new)
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("author", new Author());
        return "author/form";
    }

    // CREATE (POST /authors)
    @PostMapping
    public String create(@ModelAttribute Author author) {
        service.add(author.getId(), author);
        return "redirect:/authors";
    }

    // DELETE (POST /authors/{id}/delete)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/authors";
    }
}
