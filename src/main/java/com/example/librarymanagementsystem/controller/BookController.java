package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.BookAuthorService;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // LISTA — primit direct sortată din service
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", bookService.newForForm());
        return "book/form";
    }

    @PostMapping
    public String create(@ModelAttribute BookDetails b) {
        bookService.save(b);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        BookDetails book = bookService.getById(id);
        if (book == null) return "redirect:/books";

        model.addAttribute("book", book);
        return "book/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute BookDetails book) {
        book.setId(id);
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        BookDetails b = bookService.getById(id);
        if (b == null) return "redirect:/books";

        model.addAttribute("book", b);
        model.addAttribute("authors", bookAuthorService.getAuthorsForBook(id));

        return "book/details";
    }
}
