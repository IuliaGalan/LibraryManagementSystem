package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.BookRepo;

public class BookService extends BaseService<BookDetails> {

    public BookService(BookRepo repo) {
        super(repo);
    }
}
