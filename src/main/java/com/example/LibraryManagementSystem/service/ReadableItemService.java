package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.repository.ReadableItemRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadableItemService {

    private final ReadableItemRepo repo;

    public ReadableItemService(ReadableItemRepo repo) {
        this.repo = repo;
    }

    // ✅ METODĂ VECHE (compatibilitate)
    public List<ReadableItem> getAll() {
        return repo.findAllSorted();
    }

    // ✅ METODĂ NOUĂ - CU SORTARE ȘI FILTRARE
    public List<ReadableItem> getAll(String sortBy, String direction,
                                     String filterPublicationId,
                                     String filterBarcode,
                                     String filterStatus) {

        // 1️⃣ CONSTRUIEȘTE SORTAREA
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // 2️⃣ VERIFICĂ CARE FILTRE SUNT ACTIVE
        boolean hasPublicationIdFilter = filterPublicationId != null && !filterPublicationId.trim().isEmpty();
        boolean hasBarcodeFilter = filterBarcode != null && !filterBarcode.trim().isEmpty();
        boolean hasStatusFilter = filterStatus != null && !filterStatus.trim().isEmpty();

        // Convert status string to enum if needed
        ReadableItem.ItemStatus status = null;
        if (hasStatusFilter) {
            try {
                status = ReadableItem.ItemStatus.valueOf(filterStatus.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                hasStatusFilter = false;
            }
        }

        // 3️⃣ APLICĂ FILTRELE CORESPUNZĂTOARE

        // TOATE 3 FILTRE
        if (hasPublicationIdFilter && hasBarcodeFilter && hasStatusFilter) {
            return repo.findByPublicationIdContainingIgnoreCaseAndBarcodeContainingIgnoreCaseAndStatus(
                    filterPublicationId.trim(), filterBarcode.trim(), status, sort);
        }

        // 2 FILTRE: PublicationId + Barcode
        if (hasPublicationIdFilter && hasBarcodeFilter) {
            return repo.findByPublicationIdContainingIgnoreCaseAndBarcodeContainingIgnoreCase(
                    filterPublicationId.trim(), filterBarcode.trim(), sort);
        }

        // 2 FILTRE: PublicationId + Status
        if (hasPublicationIdFilter && hasStatusFilter) {
            return repo.findByPublicationIdContainingIgnoreCaseAndStatus(
                    filterPublicationId.trim(), status, sort);
        }

        // 2 FILTRE: Barcode + Status
        if (hasBarcodeFilter && hasStatusFilter) {
            return repo.findByBarcodeContainingIgnoreCaseAndStatus(
                    filterBarcode.trim(), status, sort);
        }

        // 1 FILTRU: Doar PublicationId
        if (hasPublicationIdFilter) {
            return repo.findByPublicationIdContainingIgnoreCase(filterPublicationId.trim(), sort);
        }

        // 1 FILTRU: Doar Barcode
        if (hasBarcodeFilter) {
            return repo.findByBarcodeContainingIgnoreCase(filterBarcode.trim(), sort);
        }

        // 1 FILTRU: Doar Status
        if (hasStatusFilter) {
            return repo.findByStatus(status, sort);
        }

        // FĂRĂ FILTRE: Doar sortare
        return repo.findAll(sort);
    }

    public ReadableItem getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public ReadableItem save(ReadableItem item) {
        return repo.save(item);
    }

    public void add(String id, ReadableItem item) {
        item.setId(id);
        repo.save(item);
    }

    public void update(String id, ReadableItem item) {
        item.setId(id);
        repo.save(item);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    // ✅ GENERARE ID ÎN FORMATUL CORECT: ITEM001, ITEM002, etc.
    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(ReadableItem::getId)
                .filter(id -> id != null && id.toUpperCase().startsWith("ITEM"))
                .map(id -> {
                    // Extrage partea numerică (ultimele 3 cifre)
                    String numericPart = id.substring(4); // după "ITEM"
                    try {
                        return Integer.parseInt(numericPart);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0);

        // Generează ID-ul cu padding de 3 cifre: ITEM001, ITEM002, etc.
        return String.format("ITEM%03d", maxNumber + 1);
    }

    public ReadableItem newForForm() {
        ReadableItem item = new ReadableItem();
        item.setId(generateNextId());
        return item;
    }

    // ✅ VALIDĂRI BUSINESS

    // Verifică dacă ID-ul există deja
    public boolean existsById(String id) {
        if (id == null) return false;
        return repo.existsById(id);
    }

    // Verifică dacă ID-ul este în formatul corect
    public boolean isValidIdFormat(String id) {
        if (id == null || id.isBlank()) return false;
        // Format: ITEM + 3 cifre (ITEM001, ITEM002, etc.)
        return id.toUpperCase().matches("^ITEM\\d{3}$");
    }

    // Verifică dacă barcode-ul există deja
    public boolean existsByBarcode(String barcode) {
        if (barcode == null) return false;
        return repo.findByBarcode(barcode.trim()) != null;
    }

    // Verifică dacă barcode-ul este folosit de alt item (pentru update)
    public boolean existsByBarcodeForOtherItem(String barcode, String excludedId) {
        if (barcode == null) return false;
        ReadableItem existing = repo.findByBarcode(barcode.trim());
        return existing != null && !existing.getId().equals(excludedId);
    }
}