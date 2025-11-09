package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService service, BookAuthorService bookAuthorService) {
        this.service = service;
        this.bookAuthorService = bookAuthorService;
    }

    // LISTARE: afișează toate cărțile și autorii fiecăreia
    @GetMapping
    public String getAll(Model model) {
        var books = service.getAll();
        Map<String, List<Author>> authorsByBook = new LinkedHashMap<>();

        for (var b : books) {
            authorsByBook.put(b.getId(), bookAuthorService.getAuthorsOfBook(b.getId()));
        }

        model.addAttribute("books", books);
        model.addAttribute("authorsByBook", authorsByBook);
        return "book/index";
    }

    // FORMULAR pentru adăugare carte nouă
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", new BookDetails());
        return "book/form"; // templates/book/form.html
    }

    // CREARE carte
    @PostMapping
    public String create(@ModelAttribute BookDetails book) {
        service.add(book.getId(), book);
        return "redirect:/books";
    }

    // ȘTERGERE carte
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/books";
    }
}
