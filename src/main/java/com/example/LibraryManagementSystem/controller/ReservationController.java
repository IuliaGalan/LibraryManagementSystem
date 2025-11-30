package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")  // ✅ PLURAL
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("reservations", service.getAll());
        return "reservation/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("reservation", service.newForForm());
        model.addAttribute("statuses", Reservation.ReservationStatus.values());
        return "reservation/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute Reservation reservation) {
        service.add(reservation.getId(), reservation);
        return "redirect:/reservations";  // ✅ PLURAL
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";  // ✅ PLURAL
        }
        model.addAttribute("reservation", reservation);
        return "reservation/details";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";  // ✅ PLURAL
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("statuses", Reservation.ReservationStatus.values());
        return "reservation/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Reservation reservation) {
        reservation.setId(id);
        service.update(id, reservation);
        return "redirect:/reservations";  // ✅ PLURAL
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/reservations";  // ✅ PLURAL
    }
}