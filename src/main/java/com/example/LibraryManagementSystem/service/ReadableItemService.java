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

    // LIST
    public List<ReadableItem> getAll() {
        return repo.findAll();
    }

    // GET BY ID
    public ReadableItem getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE
    public ReadableItem save(ReadableItem item) {
        return repo.save(item);
    }

    // CREATE cu ID explicit
    public void add(String id, ReadableItem item) {
        item.setId(id);
        repo.save(item);
    }

    // UPDATE
    public void update(String id, ReadableItem item) {
        item.setId(id);
        repo.save(item);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // GENERATE NEXT ID
    public String generateNextId() {
        return "ITEM" + String.format("%03d", repo.count() + 1);
    }

    // FOR FORM
    public ReadableItem newForForm() {
        ReadableItem item = new ReadableItem();
        item.setId(generateNextId());
        return item;
    }
}