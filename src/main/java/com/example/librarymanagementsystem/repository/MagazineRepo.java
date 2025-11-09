package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class MagazineRepo extends InFileRepository<MagazineDetails> {
    public MagazineRepo() {
        super("src/main/resources/data/magazine.json",
                new TypeReference<java.util.List<MagazineDetails>>() {});
    }
}
