package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;
    public AuthorController(AuthorService service) { this.service = service; }

    @GetMapping("/hello") @ResponseBody
    public String hello() { return "AuthorController OK"; }

    @GetMapping @ResponseBody
    public List<Author> getAll() { return service.getAll(); }

    @GetMapping("/{id}") @ResponseBody
    public Author getOne(@PathVariable String id) { return service.getById(id); }

    @PostMapping @ResponseBody
    public Author create(@RequestBody Author author) {
        service.add(author.getId(), author);
        return service.getById(author.getId());
    }

    @PutMapping("/{id}") @ResponseBody
    public Author update(@PathVariable String id, @RequestBody Author body) {
        service.update(id, body);
        return service.getById(id);
    }

    @DeleteMapping("/{id}") @ResponseBody
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Deleted author " + id;
    }
}
