package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.repository.LibraryRepo;

public class LibraryService extends BaseService<Library> {

    public LibraryService(LibraryRepo repo) {
        super(repo);
    }
}
