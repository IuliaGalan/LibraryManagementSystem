package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService service;
    public PublicationController(PublicationService service) {
        this.service = service;
    }

    @GetMapping("/hello") @ResponseBody
    public String hello() {
        return "PublicationController OK";
    }

    @GetMapping @ResponseBody
    public List<Publication> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}") @ResponseBody
    public Publication getOne(@PathVariable String id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}") @ResponseBody
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Deleted publication " + id;
    }
}
