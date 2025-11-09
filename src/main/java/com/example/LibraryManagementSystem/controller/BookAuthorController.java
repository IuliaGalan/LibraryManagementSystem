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

    // LIST: GET /bookauthors – tabel cu asocierile carte–autor
    @GetMapping
    public String index(Model model) {
        var links = bookAuthorService.getAll(); // (bookId, authorId)

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
        return "bookAuthor/index"; // templates/bookAuthor/index.html
    }

    // FORM: GET /bookauthors/new  (opțional: preselectare prin query params)
    @GetMapping("/new")
    //încearcă să ia din URL valoarea lui bookId si authorId
    public String newLinkForm(@RequestParam(required = false) String bookId,
                              @RequestParam(required = false) String authorId,
                              Model model) {
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("selectedBookId", bookId);
        model.addAttribute("selectedAuthorId", authorId);
        return "bookAuthor/form"; // templates/bookAuthor/form.html
    }

    // CREATE: POST /bookauthors
    @PostMapping
    public String createLink(@RequestParam String bookId, @RequestParam String authorId) {
        bookAuthorService.link(bookId, authorId);
        return "redirect:/bookauthors";
    }

    // DELETE: POST /bookauthors/delete
    // sterge o legatura carte-autor
    @PostMapping("/delete")
    public String deleteLink(@RequestParam String bookId, @RequestParam String authorId) {
        bookAuthorService.unlink(bookId, authorId);
        return "redirect:/bookauthors";
    }

    // DTO pentru view
    // necesar pt a trimite in view informatii combinate
    // acest obiect tine toate informatiile impreuna
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
