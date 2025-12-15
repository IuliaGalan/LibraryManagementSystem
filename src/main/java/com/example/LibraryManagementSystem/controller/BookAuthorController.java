package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService.BookAuthorRow;
import com.example.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // DTO pentru formular
    public static class BookAuthorForm {
        private String id;
        @NotBlank(message = "Book is required.")
        private String bookId;
        @NotBlank(message = "Author is required.")
        private String authorId;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        public String getAuthorId() { return authorId; }
        public void setAuthorId(String authorId) { this.authorId = authorId; }
    }

    // ========================================
    // ✅ MODIFICAT - LISTA CU SORTARE ȘI FILTRARE
    // ========================================
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterBookTitle,
            @RequestParam(required = false) String filterAuthorName,
            Model model) {

        List<BookAuthorRow> rows = linkService.getAllRows(sort, direction, filterBookTitle, filterAuthorName);

        model.addAttribute("rows", rows);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterBookTitle", filterBookTitle);
        model.addAttribute("filterAuthorName", filterAuthorName);

        return "bookauthor/index";
    }

    // ✅ RESTUL METODELOR (neschimbate)
    @GetMapping("/new")
    public String newForm(Model model) {
        BookAuthor ba = linkService.newForForm();
        BookAuthorForm form = new BookAuthorForm();
        form.setId(ba.getId());

        model.addAttribute("form", form);
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "bookauthor/form";
    }

    @PostMapping
    public String create(@ModelAttribute("form") @Valid BookAuthorForm form,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "bookauthor/form";
        }

        try {
            String id = (form.getId() != null && !form.getId().isBlank())
                    ? form.getId()
                    : linkService.generateNextId();

            linkService.add(id, form.getBookId(), form.getAuthorId());
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("linkError", ex.getMessage());
            model.addAttribute("books", bookService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "bookauthor/form";
        }

        return "redirect:/bookauthors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        linkService.delete(id);
        return "redirect:/bookauthors";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        BookAuthor link = linkService.getById(id);
        if (link == null) return "redirect:/bookauthors";

        BookAuthorForm form = new BookAuthorForm();
        form.setId(link.getId());
        form.setBookId(link.getBook().getId());
        form.setAuthorId(link.getAuthor().getId());

        model.addAttribute("form", form);
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("authors", authorService.getAll());

        return "bookauthor/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("form") @Valid BookAuthorForm form,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "bookauthor/edit";
        }

        try {
            linkService.update(id, form.getBookId(), form.getAuthorId());
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("linkError", ex.getMessage());
            model.addAttribute("books", bookService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "bookauthor/edit";
        }

        return "redirect:/bookauthors";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        BookAuthorRow row = linkService.getRowById(id);
        if (row == null) return "redirect:/bookauthors";

        model.addAttribute("row", row);
        return "bookauthor/details";
    }
}