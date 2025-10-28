package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.ReadableItem;
import com.example.librarymanagementsystem.repository.ReadableItemRepo;
import org.springframework.stereotype.Service;

@Service
public class ReadableItemService extends BaseService<ReadableItem> {

    public ReadableItemService(ReadableItemRepo repo) {
        super(repo);
    }
}
