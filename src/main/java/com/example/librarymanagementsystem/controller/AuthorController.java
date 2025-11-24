package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

    public AuthorController(AuthorService authorService,
                            BookAuthorService bookAuthorService) {
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
    }

    // LIST
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "author/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String newAuthorForm(Model model) {
        model.addAttribute("author", authorService.newForForm());
        return "author/form";
    }

    // CREATE
    @PostMapping
    public String createAuthor(@ModelAttribute("author") Author a) {
        authorService.add(a.getId(), a);
        return "redirect:/authors";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable String id) {
        authorService.delete(id);
        return "redirect:/authors";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editAuthorForm(@PathVariable String id, Model model) {
        Author author = authorService.getById(id);
        if (author == null) return "redirect:/authors";
        model.addAttribute("author", author);
        return "author/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String updateAuthor(@PathVariable String id,
                               @ModelAttribute("author") Author author) {
        author.setId(id);
        authorService.update(id, author);
        return "redirect:/authors";
    }

    // DETAILS – aici adăugăm lista de cărți ale autorului
    @GetMapping("/{id}/details")
    public String authorDetails(@PathVariable String id, Model model) {
        Author author = authorService.getById(id);
        if (author == null) return "redirect:/authors";

        model.addAttribute("author", author);

        // 👇 toate cărțile pentru acest autor
        List<BookDetails> books = bookAuthorService.getBooksForAuthor(id);
        model.addAttribute("books", books);

        return "author/details";
    }
}
