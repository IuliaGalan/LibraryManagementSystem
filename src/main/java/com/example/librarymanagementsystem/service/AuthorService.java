package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.AuthorRepo;

public class AuthorService extends BaseService<Author> {

    public AuthorService(AuthorRepo repo) {
        super(repo);
    }
}
