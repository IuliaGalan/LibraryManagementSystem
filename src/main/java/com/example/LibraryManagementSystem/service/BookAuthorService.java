package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.repository.BookAuthorRepo;
import org.springframework.stereotype.Service;

@Service
public class BookAuthorService extends BaseService<BookAuthor> {
    public BookAuthorService(BookAuthorRepo repo) { super(repo); }
}
