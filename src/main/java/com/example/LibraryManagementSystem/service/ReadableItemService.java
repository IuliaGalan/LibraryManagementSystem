package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.repository.ReadableItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadableItemService {

    private final ReadableItemRepo repo;

    public ReadableItemService(ReadableItemRepo repo) {
        this.repo = repo;
    }

    // READ - toate itemele
    public List<ReadableItem> getAll() {
        return repo.findAll();
    }

    // READ - un item după ID
    public ReadableItem getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE & UPDATE
    public ReadableItem save(ReadableItem item) {
        return repo.save(item);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // Generare ID automat
    public String generateNextId() {
        return "ITEM" + (repo.count() + 1);
    }

    // Helper pentru formular nou
    public ReadableItem newForForm() {
        ReadableItem item = new ReadableItem();
        item.setId(generateNextId());
        return item;
    }

    // --- Metode pentru relații ---

    // Găsește toate itemele dintr-o bibliotecă
    public List<ReadableItem> getItemsByLibrary(String libraryId) {
        return repo.findByLibrary_Id(libraryId);
    }

    // Găsește toate itemele unei publicații
    public List<ReadableItem> getItemsByPublication(String publicationId) {
        return repo.findByPublicationId(publicationId);
    }

    // Găsește itemele după status
    public List<ReadableItem> getItemsByStatus(ReadableItem.ItemStatus status) {
        return repo.findByStatus(status);
    }

    // Găsește un item după barcode
    public ReadableItem getByBarcode(String barcode) {
        return repo.findByBarcode(barcode);
    }

    // --- Validări business ---

    // Verifică dacă există un item cu acest barcode (pentru CREATE)
    public boolean existsByBarcode(String barcode) {
        if (barcode == null) return false;
        return repo.existsByBarcode(barcode.trim());
    }

    // Verifică dacă există alt item cu acest barcode (pentru UPDATE)
    public boolean existsByBarcodeForOtherItem(String barcode, String excludedId) {
        if (barcode == null) return false;
        return repo.existsByBarcodeAndIdNot(barcode.trim(), excludedId);
    }
}