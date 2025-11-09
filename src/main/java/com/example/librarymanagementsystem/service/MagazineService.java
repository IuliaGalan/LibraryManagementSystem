package com.example.librarymanagementsystem.service;
import java.util.Objects;
import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.repository.MagazineRepo;
import org.springframework.stereotype.Service;

@Service
public class MagazineService extends BaseService<MagazineDetails> {

    public MagazineService(MagazineRepo repo) {
        super(repo);
    }

    public String generateNextId() {
        int next = repo.findAll().stream()
                .map(MagazineDetails::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.startsWith("M"))
                .map(id -> id.substring(1))
                .filter(s -> s.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;
        return "M" + next;
    }

    /** creează un obiect gol cu ID precompletat pentru formular */
    public MagazineDetails newForForm() {
        MagazineDetails m = new MagazineDetails();
        m.setId(generateNextId());
        return m;
    }
}
