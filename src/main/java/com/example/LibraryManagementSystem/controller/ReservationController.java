package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.service.ReservationService;
import com.example.librarymanagementsystem.service.MemberService;
import com.example.librarymanagementsystem.service.ReadableItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;
    private final MemberService memberService;
    private final ReadableItemService itemService;

    public ReservationController(ReservationService service,
                                 MemberService memberService,
                                 ReadableItemService itemService) {
        this.service = service;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    // READ - toate rezervările
    @GetMapping
    public String getAll(Model model) {
        List<Reservation> reservations = service.getAll();
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }

    // CREATE - afișează formularul
    @GetMapping("/new")
    public String form(Model model) {
        Reservation reservation = service.newForForm();
        List<Member> members = memberService.getAll();
        List<ReadableItem> items = itemService.getAll();

        model.addAttribute("reservation", reservation);
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "reservation/form";
    }

    // CREATE - salvează rezervarea nouă
    @PostMapping
    public String create(@ModelAttribute Reservation reservation) {
        service.save(reservation);
        return "redirect:/reservations";
    }

    // READ - detalii rezervare
    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        return "reservation/details";
    }

    // UPDATE - afișează formularul de editare
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }

        List<Member> members = memberService.getAll();
        List<ReadableItem> items = itemService.getAll();

        model.addAttribute("reservation", reservation);
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "reservation/form";
    }

    // UPDATE - salvează modificările
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute Reservation reservation) {
        reservation.setId(id);
        service.save(reservation);
        return "redirect:/reservations";
    }

    // DELETE - șterge rezervarea
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/reservations";
    }

    // Filtrare rezervări după membru
    @GetMapping("/member/{memberId}")
    public String getByMember(@PathVariable String memberId, Model model) {
        List<Reservation> reservations = service.getReservationsByMember(memberId);
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }

    // Filtrare rezervări după item
    @GetMapping("/item/{itemId}")
    public String getByItem(@PathVariable String itemId, Model model) {
        List<Reservation> reservations = service.getReservationsByItem(itemId);
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }

    // Filtrare rezervări după status
    @GetMapping("/status/{status}")
    public String getByStatus(@PathVariable String status, Model model) {
        List<Reservation> reservations = service.getReservationsByStatus(status);
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }
}