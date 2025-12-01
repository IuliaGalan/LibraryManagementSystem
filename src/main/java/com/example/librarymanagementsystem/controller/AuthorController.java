package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // LISTA AUTORILOR — deja sortați corect prin service
    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "author/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", authorService.newForForm());
        return "author/form";
    }

    @PostMapping
    public String create(@ModelAttribute Author a) {
        authorService.save(a);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        authorService.delete(id);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Author author = authorService.getById(id);
        if (author == null) return "redirect:/authors";

        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Author a) {
        a.setId(id);
        authorService.save(a);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Author author = authorService.getById(id);
        if (author == null) return "redirect:/authors";

        model.addAttribute("author", author);
        model.addAttribute("books", bookAuthorService.getBooksForAuthor(id));
        model.addAttribute("magazine", author.getMagazine()); // poate fi null

        return "author/details";
    }
}
