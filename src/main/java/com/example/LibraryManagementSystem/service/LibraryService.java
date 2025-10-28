package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Library;
import com.example.librarymanagementsystem.repository.LibraryRepo;
import org.springframework.stereotype.Service;

@Service
public class LibraryService extends BaseService<Library> {
    public LibraryService(LibraryRepo repo) {
        super(repo);
    }
}
