package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.service.MemberService;
import com.example.librarymanagementsystem.service.ReadableItemService;
import com.example.librarymanagementsystem.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")  // plural
public class ReservationController {

    private final ReservationService service;
    private final MemberService memberService;
    private final ReadableItemService readableItemService;

    public ReservationController(ReservationService service,
                                 MemberService memberService,
                                 ReadableItemService readableItemService) {
        this.service = service;
        this.memberService = memberService;
        this.readableItemService = readableItemService;
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
        model.addAttribute("members", memberService.getAll());
        model.addAttribute("items", readableItemService.getAll());
        return "reservation/form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute Reservation reservation,
                         @RequestParam("memberId") String memberId,
                         @RequestParam("readableItemId") String readableItemId) {

        reservation.setMember(memberService.getById(memberId));
        reservation.setReadableItem(readableItemService.getById(readableItemId));

        service.add(reservation.getId(), reservation);
        return "redirect:/reservations";
    }

    // DETAILS
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        return "reservation/details";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("statuses", Reservation.ReservationStatus.values());
        model.addAttribute("members", memberService.getAll());
        model.addAttribute("items", readableItemService.getAll());
        return "reservation/edit";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Reservation reservation,
                         @RequestParam("memberId") String memberId,
                         @RequestParam("readableItemId") String readableItemId) {

        reservation.setId(id);
        reservation.setMember(memberService.getById(memberId));
        reservation.setReadableItem(readableItemService.getById(readableItemId));

        service.update(id, reservation);
        return "redirect:/reservations";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/reservations";
    }
}
