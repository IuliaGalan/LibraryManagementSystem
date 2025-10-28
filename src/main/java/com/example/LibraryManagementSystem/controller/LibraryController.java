package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "LibraryController works!";
    }

    // returnează toate bibliotecile
    @GetMapping
    public List<Library> getAllLibraries() {
        return service.getAll();
    }

    // adaugă o bibliotecă nouă
    @PostMapping
    public void addLibrary(@RequestBody Library library) {
        service.add(library.getId(), library);
    }

    // găsește o bibliotecă după id
    @GetMapping("/{id}")
    public Library getLibraryById(@PathVariable String id) {
        return service.getById(id);
    }

    // șterge o bibliotecă
    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable String id) {
        service.delete(id);
    }
}
