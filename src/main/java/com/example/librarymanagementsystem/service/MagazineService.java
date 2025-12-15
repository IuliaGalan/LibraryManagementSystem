package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.repository.MagazineRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineService {

    private final MagazineRepo repo;

    public MagazineService(MagazineRepo repo) {
        this.repo = repo;
    }

    // ✅ METODĂ VECHE (păstrată)
    public List<MagazineDetails> getAll() {
        return repo.findAllSorted();
    }

    // ========================================
    // ✅ METODĂ NOUĂ - CU SORTARE ȘI FILTRARE
    // ========================================
    public List<MagazineDetails> getAll(String sortBy, String direction,
                                        String filterTitle, String filterPublisher, String filterLanguage) {

        // 1️⃣ Construiește sortarea
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // 2️⃣ Verifică filtrele active
        boolean hasTitleFilter = filterTitle != null && !filterTitle.trim().isEmpty();
        boolean hasPublisherFilter = filterPublisher != null && !filterPublisher.trim().isEmpty();
        boolean hasLanguageFilter = filterLanguage != null && !filterLanguage.trim().isEmpty();

        // 3️⃣ Aplică filtrele corespunzătoare
        if (hasTitleFilter && hasPublisherFilter && hasLanguageFilter) {
            return repo.findByTitleContainingIgnoreCaseAndPublisherContainingIgnoreCaseAndLanguageContainingIgnoreCase(
                    filterTitle.trim(), filterPublisher.trim(), filterLanguage.trim(), sort);
        } else if (hasTitleFilter && hasPublisherFilter) {
            return repo.findByTitleContainingIgnoreCaseAndPublisherContainingIgnoreCase(
                    filterTitle.trim(), filterPublisher.trim(), sort);
        } else if (hasTitleFilter && hasLanguageFilter) {
            return repo.findByTitleContainingIgnoreCaseAndLanguageContainingIgnoreCase(
                    filterTitle.trim(), filterLanguage.trim(), sort);
        } else if (hasPublisherFilter && hasLanguageFilter) {
            return repo.findByPublisherContainingIgnoreCaseAndLanguageContainingIgnoreCase(
                    filterPublisher.trim(), filterLanguage.trim(), sort);
        } else if (hasTitleFilter) {
            return repo.findByTitleContainingIgnoreCase(filterTitle.trim(), sort);
        } else if (hasPublisherFilter) {
            return repo.findByPublisherContainingIgnoreCase(filterPublisher.trim(), sort);
        } else if (hasLanguageFilter) {
            return repo.findByLanguageContainingIgnoreCase(filterLanguage.trim(), sort);
        } else {
            return repo.findAll(sort);
        }
    }

    // ✅ RESTUL METODELOR (neschimbate)
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

    public boolean existsByTitle(String title) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCase(title.trim());
    }

    public boolean existsByTitleForOtherMagazine(String title, String excludedId) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCaseAndIdNot(title.trim(), excludedId);
    }
}