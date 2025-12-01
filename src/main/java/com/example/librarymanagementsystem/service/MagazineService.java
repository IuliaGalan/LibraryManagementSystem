package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.repository.MagazineRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineService {

    private final MagazineRepo repo;

    public MagazineService(MagazineRepo repo) {
        this.repo = repo;
    }

    // LISTĂ SORTATĂ NATURAL: M1, M2, ..., M10
    public List<MagazineDetails> getAll() {
        return repo.findAllSorted();
    }

    public MagazineDetails getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public MagazineDetails save(MagazineDetails m) {
        return repo.save(m);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        return "M" + (repo.count() + 1);
    }

    public MagazineDetails newForForm() {
        MagazineDetails m = new MagazineDetails();
        m.setId(generateNextId());
        return m;
    }

    // --- Business validation helpers ---

    public boolean existsByTitle(String title) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCase(title.trim());
    }

    public boolean existsByTitleForOtherMagazine(String title, String excludedId) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCaseAndIdNot(title.trim(), excludedId);
    }
}
