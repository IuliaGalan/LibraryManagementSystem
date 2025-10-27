package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    public BookController(BookService service) { this.service = service; }

    @GetMapping("/hello") @ResponseBody
    public String hello() { return "BookController OK"; }

    @GetMapping @ResponseBody
    public List<BookDetails> getAll() { return service.getAll(); }

    @GetMapping("/{id}") @ResponseBody
    public BookDetails getOne(@PathVariable String id) { return service.getById(id); }

    @PostMapping @ResponseBody
    public BookDetails create(@RequestBody BookDetails book) {
        service.add(book.getId(), book);
        return service.getById(book.getId());
    }

    @PutMapping("/{id}") @ResponseBody
    public BookDetails update(@PathVariable String id, @RequestBody BookDetails body) {
        service.update(id, body);
        return service.getById(id);
    }

    @DeleteMapping("/{id}") @ResponseBody
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Deleted book " + id;
    }
}
