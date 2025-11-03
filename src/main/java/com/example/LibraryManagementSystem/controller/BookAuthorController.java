package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bookauthors")
public class BookAuthorController {
    private final BookAuthorService bookAuthorService;
    private final BookService bookService;
    private final AuthorService authorService;

    public BookAuthorController(BookAuthorService bookAuthorService,
                                BookService bookService,
                                AuthorService authorService) {
        this.bookAuthorService = bookAuthorService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // ✅ LIST: GET /bookauthors – tabel cu link-urile carte–autor
    @GetMapping
    public String index(Model model) {
        var links = bookAuthorService.getAll(); // presupusă: listează legăturile (bookId, authorId)

        // construim rânduri cu titlu/nume pentru afișare
        List<Row> rows = new ArrayList<>();
        for (var link : links) {
            var b = bookService.getById(link.getBookId());
            var a = authorService.getById(link.getAuthorId());
            rows.add(new Row(
                    link.getBookId(),
                    (b != null ? b.getTitle() : "(unknown)"),
                    link.getAuthorId(),
                    (a != null ? a.getName() : "(unknown)")
            ));
        }
        model.addAttribute("rows", rows);
        return "bookAuthor/index";
    }

    // FORMULAR: GET /bookauthors/new
    @GetMapping("/new")
    public String newLinkForm(Model model) {
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "bookAuthor/form"; // asigură-te că ai templates/bookAuthor/form.html
    }

    // CREATE: POST /bookauthors
    @PostMapping
    public String createLink(@RequestParam String bookId, @RequestParam String authorId) {
        bookAuthorService.link(bookId, authorId);
        return "redirect:/bookauthors"; // după creare revii la tabel
    }

    // DELETE: POST /bookauthors/delete
    @PostMapping("/delete")
    public String deleteLink(@RequestParam String bookId, @RequestParam String authorId) {
        bookAuthorService.unlink(bookId, authorId);
        return "redirect:/bookauthors";
    }

    // mic DTO pentru view
    public static class Row {
        private final String bookId;
        private final String bookTitle;
        private final String authorId;
        private final String authorName;
        public Row(String bookId, String bookTitle, String authorId, String authorName) {
            this.bookId = bookId; this.bookTitle = bookTitle;
            this.authorId = authorId; this.authorName = authorName;
        }
        public String getBookId() { return bookId; }
        public String getBookTitle() { return bookTitle; }
        public String getAuthorId() { return authorId; }
        public String getAuthorName() { return authorName; }
    }
}
