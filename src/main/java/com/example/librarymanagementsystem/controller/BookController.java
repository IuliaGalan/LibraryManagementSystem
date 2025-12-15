package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.BookAuthorService;
import com.example.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    //LIST
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterTitle,
            @RequestParam(required = false) String filterGenre,
            Model model) {

        List<BookDetails> books = bookService.getAll(sort, direction, filterTitle, filterGenre);

        model.addAttribute("books", books);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterTitle", filterTitle);
        model.addAttribute("filterGenre", filterGenre);

        return "book/index";
    }

    //CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", bookService.newForForm());
        return "book/form";
    }

    //VALIDARE TITLE DUPLICAT
    @PostMapping
    public String create(@Valid @ModelAttribute BookDetails book,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        //VALIDARE: Titlu duplicat?
        if (book.getTitle() != null && !book.getTitle().isBlank()) {
            if (bookService.existsByTitle(book.getTitle())) {
                bindingResult.rejectValue("title", "error.book",
                        "This book title already exists.");
            }
        }

        if (bindingResult.hasErrors()) {
            return "book/form";
        }

        bookService.save(book);

        // ✅ MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Book '" + book.getTitle() + "' created successfully!");

        return "redirect:/books";
    }

    //DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        BookDetails b = bookService.getById(id);
        if (b == null) return "redirect:/books";

        model.addAttribute("book", b);
        model.addAttribute("authors", bookAuthorService.getAuthorsForBook(id));

        return "book/details";
    }

    //EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        BookDetails book = bookService.getById(id);
        if (book == null) return "redirect:/books";
        model.addAttribute("book", book);
        return "book/edit";
    }

    //UPDATE - CU VALIDARE TITLE DUPLICAT
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute BookDetails book,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        book.setId(id);

        //VALIDARE: Altă carte are deja acest titlu?
        if (book.getTitle() != null && !book.getTitle().isBlank()) {
            if (bookService.existsByTitleForOtherBook(book.getTitle(), id)) {
                bindingResult.rejectValue("title", "error.book",
                        "This book title is already used by another book.");
            }
        }

        if (bindingResult.hasErrors()) {
            return "book/edit";
        }

        bookService.save(book);

        //MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Book '" + book.getTitle() + "' updated successfully!");

        return "redirect:/books";
    }

    //DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        BookDetails book = bookService.getById(id);
        String bookTitle = (book != null) ? book.getTitle() : "Book";

        bookService.delete(id);

        //MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Book '" + bookTitle + "' deleted successfully!");

        return "redirect:/books";
    }
}