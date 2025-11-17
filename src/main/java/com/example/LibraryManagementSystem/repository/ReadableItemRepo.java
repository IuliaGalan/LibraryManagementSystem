package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.ReadableItem ;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class ReadableItemRepo extends InFileRepository<ReadableItem> {
    public ReadableItemRepo() {
        super("src/main/resources/data/readableitem.json",
                new TypeReference<java.util.List<ReadableItem>>() {
                });
    }
}