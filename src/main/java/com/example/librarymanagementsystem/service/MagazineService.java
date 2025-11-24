package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.repository.RepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MagazineService extends BaseService<MagazineDetails> {

    public MagazineService(RepositoryInterface<MagazineDetails> repo) {
        super(repo);
    }

    // Generează ID-uri de forma M1, M2, M3...
    public String generateNextId() {
        int next = repo.findAll().stream()
                .map(MagazineDetails::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.startsWith("M"))
                .map(id -> id.substring(1))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;

        return "M" + next;
    }

    // Creează un obiect gol pentru formular, cu ID precompletat
    public MagazineDetails newForForm() {
        MagazineDetails m = new MagazineDetails();
        m.setId(generateNextId());
        return m;
    }
}
