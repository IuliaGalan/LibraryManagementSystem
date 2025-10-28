//package com.example.librarymanagementsystem.controller;
//
//import com.example.librarymanagementsystem.model.Library;
//import com.example.librarymanagementsystem.service.LibraryService;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/libraries")
//public class LibraryController {
//
//    private final LibraryService service;
//
//    public LibraryController(LibraryService service) {
//        this.service = service;
//    }
//
//    @PostMapping
//    public ResponseEntity<Library> create(@RequestBody Library body) {
//        // presupunem că Library are getId()
//        service.add(body.getId(), body);
//        return ResponseEntity.created(URI.create("/api/libraries/" + body.getId()))
//                .body(body);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Library> getById(@PathVariable String id) {
//        Library found = service.getById(id);
//        return (found != null) ? ResponseEntity.ok(found) : ResponseEntity.notFound().build();
//    }
//
//    @GetMapping
//    public List<Library> getAll() {
//        return service.getAll();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Library> update(@PathVariable String id, @RequestBody Library body) {
//        // dacă service.update returnează entitatea sau null la 404
//        Library updated = service.update(id, body);
//        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//        boolean removed = service.delete(id);
//        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }
//}
