package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.BookAuthorRepo;
import com.example.librarymanagementsystem.repository.BookRepo;
import com.example.librarymanagementsystem.repository.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookAuthorService {

    private final BookAuthorRepo repo;
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    public BookAuthorService(BookAuthorRepo repo, BookRepo bookRepo, AuthorRepo authorRepo) {
        this.repo = repo;
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public List<BookAuthor> getAll() {
        return repo.findAll();
    }

    public BookAuthor getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public BookAuthor save(BookAuthor link) {
        return repo.save(link);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        return "BA" + (repo.count() + 1);
    }

    public BookAuthor newForForm() {
        BookAuthor ba = new BookAuthor();
        ba.setId(generateNextId());
        return ba;
    }

    /**
     * Row pentru afișarea cu numele cărții și autorului în listă/detalii.
     */
    public static class BookAuthorRow {
        public String id;
        public String bookId;
        public String bookTitle;
        public String authorId;
        public String authorName;
    }

    public List<BookAuthorRow> getAllRows() {
        List<BookAuthor> links = repo.findAll();
        List<BookAuthorRow> rows = new ArrayList<>();

        for (BookAuthor l : links) {
            BookAuthorRow r = new BookAuthorRow();
            r.id = l.getId();

            BookDetails b = bookRepo.findById(l.getBookId()).orElse(null);
            Author a = authorRepo.findById(l.getAuthorId()).orElse(null);

            if (b != null) {
                r.bookId = b.getId();
                r.bookTitle = b.getTitle();
            }

            if (a != null) {
                r.authorId = a.getId();
                r.authorName = a.getName();
            }

            rows.add(r);
        }

        return rows;
    }

    public BookAuthorRow getRowById(String id) {
        BookAuthor l = repo.findById(id).orElse(null);
        if (l == null) return null;

        BookAuthorRow r = new BookAuthorRow();
        r.id = l.getId();

        BookDetails b = bookRepo.findById(l.getBookId()).orElse(null);
        Author a = authorRepo.findById(l.getAuthorId()).orElse(null);

        if (b != null) {
            r.bookId = b.getId();
            r.bookTitle = b.getTitle();
        }

        if (a != null) {
            r.authorId = a.getId();
            r.authorName = a.getName();
        }

        return r;
    }
}
