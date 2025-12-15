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

    public List<Library> getAll() {
        return repo.findAllSorted();
    }

    public List<Library> getAll(String sortBy, String direction,
                                String filterName,
                                String filterAddress,
                                String filterEmail) {
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        boolean hasNameFilter = filterName != null && !filterName.trim().isEmpty();
        boolean hasAddressFilter = filterAddress != null && !filterAddress.trim().isEmpty();
        boolean hasEmailFilter = filterEmail != null && !filterEmail.trim().isEmpty();

        if (hasNameFilter && hasAddressFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), filterEmail.trim(), sort);
        }

        if (hasNameFilter && hasAddressFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), sort);
        }

        if (hasNameFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterEmail.trim(), sort);
        }

        if (hasAddressFilter && hasEmailFilter) {
            return repo.findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterAddress.trim(), filterEmail.trim(), sort);
        }

        if (hasNameFilter) {
            return repo.findByNameContainingIgnoreCase(filterName.trim(), sort);
        }

        if (hasAddressFilter) {
            return repo.findByAddressContainingIgnoreCase(filterAddress.trim(), sort);
        }

        if (hasEmailFilter) {
            return repo.findByEmailContainingIgnoreCase(filterEmail.trim(), sort);
        }

        return repo.findAll(sort);
    }

    public Library getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Library save(Library library) {
        return repo.save(library);
    }

    public void add(String id, Library library) {
        library.setId(id);
        repo.save(library);
    }

    public void update(String id, Library library) {
        library.setId(id);
        repo.save(library);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Library::getId)
                .filter(id -> id != null && id.toUpperCase().startsWith("LIB"))
                .map(id -> {
                    String numericPart = id.substring(3);
                    try {
                        return Integer.parseInt(numericPart);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0);

        return String.format("LIB%03d", maxNumber + 1);
    }

    public Library newForForm() {
        Library library = new Library();
        library.setId(generateNextId());
        return library;
    }

    // ✅ VALIDĂRI BUSINESS
    public boolean existsById(String id) {
        if (id == null) return false;
        return repo.existsById(id);
    }

    public boolean isValidIdFormat(String id) {
        if (id == null || id.isBlank()) return false;
        return id.toUpperCase().matches("^LIB\\d{3}$");
    }

    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return repo.findByEmail(email.trim()) != null;
    }

    public boolean existsByEmailForOtherLibrary(String email, String excludedId) {
        if (email == null) return false;
        Library existing = repo.findByEmail(email.trim());
        return existing != null && !existing.getId().equals(excludedId);
    }

    public boolean existsByName(String name) {
        if (name == null) return false;
        return repo.findByName(name.trim()) != null;
    }

    public boolean existsByNameForOtherLibrary(String name, String excludedId) {
        if (name == null) return false;
        Library existing = repo.findByName(name.trim());
        return existing != null && !existing.getId().equals(excludedId);
    }
}