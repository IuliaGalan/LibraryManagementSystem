package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.service.MagazineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService service;
    public MagazineController(MagazineService service) { this.service = service; }

    @GetMapping("/hello") @ResponseBody
    public String hello() { return "MagazineController OK"; }

    @GetMapping @ResponseBody
    public List<MagazineDetails> getAll() { return service.getAll(); }

    @GetMapping("/{id}") @ResponseBody
    public MagazineDetails getOne(@PathVariable String id) { return service.getById(id); }

    @PostMapping @ResponseBody
    public MagazineDetails create(@RequestBody MagazineDetails m) {
        service.add(m.getId(), m);
        return service.getById(m.getId());
    }

    @PutMapping("/{id}") @ResponseBody
    public MagazineDetails update(@PathVariable String id, @RequestBody MagazineDetails body) {
        service.update(id, body);
        return service.getById(id);
    }

    @DeleteMapping("/{id}") @ResponseBody
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Deleted magazine " + id;
    }
}
