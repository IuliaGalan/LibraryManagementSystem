package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService.BookAuthorRow;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookauthors")
public class BookAuthorController {

    private final BookAuthorService linkService;
    private final BookService bookService;
    private final AuthorService authorService;

    public BookAuthorController(BookAuthorService linkService,
                                BookService bookService,
                                AuthorService authorService) {
        this.linkService = linkService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rows", linkService.getAllRows());
        return "bookauthor/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("link", linkService.newForForm());
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "bookauthor/form";
    }

    // CREATE
    @PostMapping
    public String create(@RequestParam String bookId,
                         @RequestParam String authorId) {

        // generăm un ID de tip BA1, BA2, ...
        String id = linkService.generateNextId();
        linkService.add(id, bookId, authorId);

        return "redirect:/bookauthors";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        linkService.delete(id);
        return "redirect:/bookauthors";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        BookAuthor link = linkService.getById(id);
        if (link == null) return "redirect:/bookauthors";

        model.addAttribute("link", link);
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("authors", authorService.getAll());

        return "bookauthor/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @RequestParam String bookId,
                         @RequestParam String authorId) {

        linkService.update(id, bookId, authorId);
        return "redirect:/bookauthors";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        BookAuthorRow row = linkService.getRowById(id);
        if (row == null) return "redirect:/bookauthors";

        model.addAttribute("row", row);
        return "bookauthor/details";
    }
}
