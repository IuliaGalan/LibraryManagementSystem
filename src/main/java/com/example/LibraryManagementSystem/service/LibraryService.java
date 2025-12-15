package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.repository.LibraryRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final LibraryRepo repo;

    public LibraryService(LibraryRepo repo) {
        this.repo = repo;
    }

    // ========================================
    // ✅ METODĂ VECHE (o păstrezi pentru compatibilitate)
    // ========================================
    public List<Library> getAll() {
        return repo.findAllSorted();
    }

    // ========================================
    // ✅ METODĂ NOUĂ - CU SORTARE ȘI FILTRARE
    // ========================================
    public List<Library> getAll(String sortBy, String direction,
                                String filterName,
                                String filterAddress,
                                String filterEmail) {

        // 1️⃣ CONSTRUIEȘTE SORTAREA
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // 2️⃣ VERIFICĂ CARE FILTRE SUNT ACTIVE
        boolean hasNameFilter = filterName != null && !filterName.trim().isEmpty();
        boolean hasAddressFilter = filterAddress != null && !filterAddress.trim().isEmpty();
        boolean hasEmailFilter = filterEmail != null && !filterEmail.trim().isEmpty();

        // 3️⃣ APLICĂ FILTRELE CORESPUNZĂTOARE

        // TOATE 3 FILTRE
        if (hasNameFilter && hasAddressFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), filterEmail.trim(), sort);
        }

        // 2 FILTRE: Nume + Adresă
        if (hasNameFilter && hasAddressFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), sort);
        }

        // 2 FILTRE: Nume + Email
        if (hasNameFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterEmail.trim(), sort);
        }

        // 2 FILTRE: Adresă + Email
        if (hasAddressFilter && hasEmailFilter) {
            return repo.findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterAddress.trim(), filterEmail.trim(), sort);
        }

        // 1 FILTRU: Doar Nume
        if (hasNameFilter) {
            return repo.findByNameContainingIgnoreCase(filterName.trim(), sort);
        }

        // 1 FILTRU: Doar Adresă
        if (hasAddressFilter) {
            return repo.findByAddressContainingIgnoreCase(filterAddress.trim(), sort);
        }

        // 1 FILTRU: Doar Email
        if (hasEmailFilter) {
            return repo.findByEmailContainingIgnoreCase(filterEmail.trim(), sort);
        }

        // FĂRĂ FILTRE: Doar sortare
        return repo.findAll(sort);
    }

    // ========================================
    // ✅ RESTUL METODELOR (le păstrezi exact așa)
    // ========================================

    public Library getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Library save(Library library) {
        return repo.save(library);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Library::getId)
                .filter(id -> id != null && id.startsWith("LIB"))
                .map(id -> id.substring(3))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "LIB" + (maxNumber + 1);
    }

    public Library newForForm() {
        Library lib = new Library();
        lib.setId(generateNextId());
        return lib;
    }

    // Business validation helpers
    public boolean existsByName(String name) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCase(name.trim());
    }

    public boolean existsByNameForOtherLibrary(String name, String excludedId) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCaseAndIdNot(name.trim(), excludedId);
    }
}