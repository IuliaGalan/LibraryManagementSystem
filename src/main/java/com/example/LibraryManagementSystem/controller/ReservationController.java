package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.service.MemberService;
import com.example.librarymanagementsystem.service.ReadableItemService;
import com.example.librarymanagementsystem.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
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

    // ========================================
    // ✅ MODIFICI METODA LIST - AICI E SCHIMBAREA PRINCIPALĂ!
    // ========================================
    @GetMapping
    public String list(
            // Parametri pentru SORTARE
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,

            // Parametri pentru FILTRARE
            @RequestParam(required = false) String filterMemberName,
            @RequestParam(required = false) String filterStatus,
            @RequestParam(required = false) String filterDate,

            Model model) {

        // 1️⃣ Obține lista sortată și filtrată
        List<Reservation> reservations = service.getAll(sort, direction,
                filterMemberName, filterStatus, filterDate);

        // 2️⃣ Trimite datele către view
        model.addAttribute("reservations", reservations);

        // 3️⃣ Trimite parametrii actuali (pentru UI să știe ce e selectat)
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);

        // 4️⃣ Trimite filtrele înapoi (ca să rămână în formular)
        model.addAttribute("filterMemberName", filterMemberName);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterDate", filterDate);

        // 5️⃣ Trimite lista de status-uri pentru dropdown
        model.addAttribute("statuses", Reservation.ReservationStatus.values());

        return "reservation/index";
    }

    // ========================================
    // ✅ RESTUL METODELOR RĂMÂN EXACT LA FEL
    // ========================================

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