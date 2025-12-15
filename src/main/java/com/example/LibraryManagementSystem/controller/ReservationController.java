package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.service.MemberService;
import com.example.librarymanagementsystem.service.ReadableItemService;
import com.example.librarymanagementsystem.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterMemberName,
            @RequestParam(required = false) String filterStatus,
            @RequestParam(required = false) String filterDate,
            Model model) {

        List<Reservation> reservations = service.getAll(sort, direction,
                filterMemberName, filterStatus, filterDate);

        model.addAttribute("reservations", reservations);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterMemberName", filterMemberName);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterDate", filterDate);
        model.addAttribute("statuses", Reservation.ReservationStatus.values());

        return "reservation/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("reservation", service.newForForm());
        model.addAttribute("members", memberService.getAll());
        model.addAttribute("items", readableItemService.getAll());
        return "reservation/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Reservation reservation,
                         BindingResult bindingResult,
                         @RequestParam("memberId") String memberId,
                         @RequestParam("readableItemId") String readableItemId,
                         @RequestParam("statusInput") String statusInput,
                         Model model) {

        // Validare: Status valid?
        Reservation.ReservationStatus validStatus = null;
        try {
            validStatus = Reservation.ReservationStatus.valueOf(statusInput.toUpperCase().trim());
            reservation.setStatus(validStatus);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("status", "error.reservation",
                    "Status must be: ACTIVE, COMPLETED, or CANCELLED");
        }

        // Set member
        reservation.setMember(memberService.getById(memberId));

        // Set readable item
        reservation.setReadableItem(readableItemService.getById(readableItemId));

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.getAll());
            model.addAttribute("items", readableItemService.getAll());
            return "reservation/form";
        }

        service.save(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        return "reservation/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Reservation reservation = service.getById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("members", memberService.getAll());
        model.addAttribute("items", readableItemService.getAll());
        return "reservation/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute Reservation reservation,
                         BindingResult bindingResult,
                         @RequestParam("memberId") String memberId,
                         @RequestParam("readableItemId") String readableItemId,
                         @RequestParam("statusInput") String statusInput,
                         Model model) {

        reservation.setId(id);

        // Validare: Status valid?
        Reservation.ReservationStatus validStatus = null;
        try {
            validStatus = Reservation.ReservationStatus.valueOf(statusInput.toUpperCase().trim());
            reservation.setStatus(validStatus);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("status", "error.reservation",
                    "Status must be: ACTIVE, COMPLETED, or CANCELLED");
        }

        // Set member
        reservation.setMember(memberService.getById(memberId));

        // Set readable item
        reservation.setReadableItem(readableItemService.getById(readableItemId));

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.getAll());
            model.addAttribute("items", readableItemService.getAll());
            return "reservation/edit";
        }

        service.save(reservation);
        return "redirect:/reservations";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/reservations";
    }
}