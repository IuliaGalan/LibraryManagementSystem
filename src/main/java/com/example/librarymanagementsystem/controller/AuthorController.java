package com.example.librarymanagementsystem.controller;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

@Controller //controleaza cererile web dintre Java si paginile HTML
//Inversion of Conrol Container
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;
    private final BookAuthorService bookAuthorService;

    public AuthorController(AuthorService service, BookAuthorService bookAuthorService) {
        this.service = service; //dependency injection
        this.bookAuthorService = bookAuthorService;
    }

    // GetAll
    // AuthorController.java (doar metoda index)
    @GetMapping
    public String getAll(Model model) {
        var authors = service.getAll();
        Map<String, List<BookDetails>> booksByAuthor = new LinkedHashMap<>();
        for (var a : authors) {
            booksByAuthor.put(a.getId(), bookAuthorService.getBooksOfAuthor(a.getId()));
        }
        model.addAttribute("authors", authors);
        model.addAttribute("booksByAuthor", booksByAuthor);
        return "author/index";
    }

    // FORM - creeaza si afiseaza un formular care cere datele unui autor
    // se creeaza un obiect gol de tip Author
    // obiectul gol se trimite in Model
    // deschide pagina author/form
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("author", new Author());
        return "author/form";
    }

    //utilizatorul completeaza formularul, iar dupa salvare, functia create() primeste datele

    // CREATE - asculta request ul de a primi date in formular
    // creeaza automat un obiect de tip Author cu valorile din form
    // dupa salvare, se apeleeaza metoda getAll
    @PostMapping
    public String create(@ModelAttribute Author author) {
        // dacă id-ul se generează în service, nu îl ceri în formular
        service.add(author.getId(), author);
        return "redirect:/authors"; //afiseaza pagina index
    }

    // DELETE (PRIN POST)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/authors";
    }
}
