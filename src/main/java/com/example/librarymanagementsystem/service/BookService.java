package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.repository.PublicationRepo;

public class BookService extends BaseService<Publication> {

    public BookService(PublicationRepo repo) {
        super(repo);
    }
}
