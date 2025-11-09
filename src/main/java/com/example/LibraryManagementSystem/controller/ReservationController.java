package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("reservations", service.getAll());
        return "reservation/index"; // templates/reservation/index.html
    }


    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation/form"; // templates/reservation/form.html
    }


    @PostMapping
    public String create(@ModelAttribute Reservation reservation) {
        if (reservation.getId() == null || reservation.getId().isBlank()) {
            reservation.setId(UUID.randomUUID().toString());
        }
        service.add(reservation.getId(), reservation);
        return "redirect:/reservation";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/reservation";
    }
}
