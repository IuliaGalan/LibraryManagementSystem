package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String hello() {
        return "ReservationController works!";
    }

    @GetMapping
    public List<Reservation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public void add(@RequestBody Reservation reservation) {
        service.add(reservation.getId(), reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
