package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.AuthorRepo;
import com.example.librarymanagementsystem.repository.BookRepo;

import java.util.List;
import java.util.ArrayList;

public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    public AuthorService(AuthorRepo authorRepo, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    public Author create(String id, String name) {
        Author a = new Author(id, name);
        authorRepo.save(id, a);
        return a;
    }

    public Author get(String id) {
        return authorRepo.findById(id);
    }

    public List<Author> all() {
        return authorRepo.findAll();
    }

    public void delete(String id) {
        Author a = authorRepo.findById(id);
        if (a == null) return;

        List<BookDetails> listaCopiataCarti = new ArrayList<>(a.getBooks());
        for (BookDetails b : listaCopiataCarti) {
            b.removeAuthor(a);
            bookRepo.save(b.getId(), b);
        }
        authorRepo.delete(id);
    }
}