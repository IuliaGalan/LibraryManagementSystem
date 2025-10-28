package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.service.ReadableItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readableItem")
public class ReadableItemController {

    private final ReadableItemService service;

    public ReadableItemController(ReadableItemService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String hello() {
        return "ReadableItemController works!";
    }

    @GetMapping
    public List<ReadableItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ReadableItem getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public void add(@RequestBody ReadableItem item) {
        service.add(item.getId(), item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
