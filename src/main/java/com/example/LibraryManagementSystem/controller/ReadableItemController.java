package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.service.ReadableItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/readableitems")
public class ReadableItemController {

    private final ReadableItemService service;

    public ReadableItemController(ReadableItemService service) {
        this.service = service;
    }

    // ========================================
    // ✅ LISTA CU SORTARE ȘI FILTRARE - FIXED
    // ========================================
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false) String filterPublicationId,
            @RequestParam(required = false) String filterBarcode,
            @RequestParam(required = false) String filterStatus,
            Model model) {

        List<ReadableItem> items = service.getAll(sort, direction,
                filterPublicationId, filterBarcode, filterStatus);

        // ✅ FIXED: changed from "items" to "readableItems"
        model.addAttribute("readableItems", items);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("filterPublicationId", filterPublicationId);
        model.addAttribute("filterBarcode", filterBarcode);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("statuses", ReadableItem.ItemStatus.values());

        return "readableitem/index";
    }

    // ========================================
    // ✅ CREATE FORM - ID GENERAT AUTOMAT
    // ========================================
    @GetMapping("/new")
    public String form(Model model) {
        ReadableItem item = service.newForForm(); // generează ID automat
        model.addAttribute("item", item);
        return "readableitem/form";
    }

    // ========================================
    // ✅ CREATE - CU VALIDĂRI
    // ========================================
    @PostMapping
    public String create(@Valid @ModelAttribute("item") ReadableItem item,
                         BindingResult bindingResult,
                         @RequestParam("statusInput") String statusInput,
                         Model model) {

        // 🔹 VALIDARE 1: Status valid?
        ReadableItem.ItemStatus validStatus = null;
        try {
            validStatus = ReadableItem.ItemStatus.valueOf(statusInput.toUpperCase().trim());
            item.setStatus(validStatus);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("status", "error.item",
                    "Status must be: ACTIVE, COMPLETED, or CANCELLED");
        }

        // 🔹 VALIDARE 2: Barcode duplicat?
        if (item.getBarcode() != null && !item.getBarcode().isBlank()) {
            if (service.existsByBarcode(item.getBarcode())) {
                bindingResult.rejectValue("barcode", "error.item",
                        "This barcode already exists.");
            }
        }

        // Dacă sunt erori, rămâi pe formular
        if (bindingResult.hasErrors()) {
            return "readableitem/form";
        }

        service.save(item);
        return "redirect:/readableitems";
    }

    // ========================================
    // ✅ DETAILS
    // ========================================
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        ReadableItem item = service.getById(id);
        if (item == null) {
            return "redirect:/readableitems";
        }
        model.addAttribute("item", item);
        return "readableitem/details";
    }

    // ========================================
    // ✅ EDIT FORM
    // ========================================
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        ReadableItem item = service.getById(id);
        if (item == null) {
            return "redirect:/readableitems";
        }
        model.addAttribute("item", item);
        return "readableitem/edit";
    }

    // ========================================
    // ✅ UPDATE - CU VALIDĂRI
    // ========================================
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("item") ReadableItem item,
                         BindingResult bindingResult,
                         @RequestParam("statusInput") String statusInput,
                         Model model) {

        item.setId(id);

        // 🔹 VALIDARE 1: Status valid?
        ReadableItem.ItemStatus validStatus = null;
        try {
            validStatus = ReadableItem.ItemStatus.valueOf(statusInput.toUpperCase().trim());
            item.setStatus(validStatus);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("status", "error.item",
                    "Status must be: ACTIVE, COMPLETED, or CANCELLED");
        }

        // 🔹 VALIDARE 2: Alt item are deja acest barcode?
        if (item.getBarcode() != null && !item.getBarcode().isBlank()) {
            if (service.existsByBarcodeForOtherItem(item.getBarcode(), id)) {
                bindingResult.rejectValue("barcode", "error.item",
                        "This barcode is already used by another item.");
            }
        }

        // Dacă sunt erori, rămâi pe edit
        if (bindingResult.hasErrors()) {
            return "readableitem/edit";
        }

        service.save(item);
        return "redirect:/readableitems";
    }

    // ========================================
    // ✅ DELETE
    // ========================================
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/readableitems";
    }
}