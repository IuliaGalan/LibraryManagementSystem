package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookAuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // afisarea listei de autori in browser (cu sortare și filtrare)
    //apelarea automata la accesarea paginii /authors
    @GetMapping
    //endpoint = GET /authors
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterName,
            @RequestParam(required = false) String filterNationality,
            Model model) {

        //cere Service ului lista sortata si filtrata
        List<Author> authors = authorService.getAll(sort, direction, filterName, filterNationality);

        //trimite datele din controller catre pagina din browser
        model.addAttribute("authors", authors);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterName", filterName);
        model.addAttribute("filterNationality", filterNationality);

        return "author/index";
    }

    //CREATE FORM
    //afiseaza formularul pentru crearea unui nou autor
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", authorService.newForForm());
        return "author/form";
    }


    //CREATE - procesarea formularului
    // VALIDARE NAME DUPLICAT
    // Buton Save -> formularul trimite request POST
    @PostMapping
    public String create(@Valid @ModelAttribute Author author,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        // VALIDARE Nume duplicat - Regula de business
        if (author.getName() != null && !author.getName().isBlank()) {
            if (authorService.existsByName(author.getName())) {
                bindingResult.rejectValue("name", "error.author",
                        "This author name already exists.");
            }
        }

        // Dacă sunt erori, rămâi pe formular
        if (bindingResult.hasErrors()) {
            return "author/form";
        }

        authorService.save(author);

        // MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Author '" + author.getName() + "' created successfully!");

        return "redirect:/authors";
    }


    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Author author = authorService.getById(id);
        if (author == null) {
            return "redirect:/authors";
        }

        model.addAttribute("author", author);
        model.addAttribute("books", bookAuthorService.getBooksForAuthor(id));
        model.addAttribute("magazine", author.getMagazine());

        return "author/details";
    }


    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Author author = authorService.getById(id);
        if (author == null) {
            return "redirect:/authors";
        }
        model.addAttribute("author", author);
        return "author/edit";
    }

    // UPDATE - CU VALIDARE NAME DUPLICAT
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute Author author,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        author.setId(id);

        // VALIDARE de business: Alt autor are deja acest nume?
        if (author.getName() != null && !author.getName().isBlank()) {
            if (authorService.existsByNameForOtherAuthor(author.getName(), id)) {
                bindingResult.rejectValue("name", "error.author",
                        "This author name is already used by another author.");
            }
        }

        // Dacă sunt erori, rămâi pe edit
        if (bindingResult.hasErrors()) {
            return "author/edit";
        }

        authorService.save(author);

        // MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                "Author '" + author.getName() + "' updated successfully!");

        return "redirect:/authors";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Author author = authorService.getById(id);
        String authorName = (author != null) ? author.getName() : "Author";

        authorService.delete(id);

        // MESAJ DE SUCCES
        redirectAttributes.addFlashAttribute("successMessage",
                authorName + " deleted successfully!");

        return "redirect:/authors";
    }
}