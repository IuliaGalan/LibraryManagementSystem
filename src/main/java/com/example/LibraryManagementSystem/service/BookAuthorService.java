package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.repository.InMemoryBaseRepo;
import com.example.librarymanagementsystem.repository.BookAuthorRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookAuthorService extends BaseService<BookAuthor> {

    private final AuthorService authorService;
    private final BookService bookService;

    public BookAuthorService(InMemoryBaseRepo<BookAuthor> repo,
                             AuthorService authorService,
                             BookService bookService) {
        super(repo);
        this.authorService = authorService;
        this.bookService = bookService;
    }

    // creează o legătură dacă nu există deja
    public boolean link(String bookId, String authorId) {
        // evităm duplicatele
        boolean exists = getAll().stream()
                .anyMatch(ba -> ba.getBookId().equals(bookId) && ba.getAuthorId().equals(authorId));
        if (exists) return false;

        // id simplu pentru BookAuthor (poți folosi și un UUID)
        String linkId = bookId + "_" + authorId;
        repo.save(linkId, new BookAuthor(linkId, bookId, authorId));
        return true;
    }

    public List<Author> getAuthorsOfBook(String bookId) {
        return getAll().stream()
                .filter(ba -> ba.getBookId().equals(bookId))
                .map(ba -> authorService.getById(ba.getAuthorId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<BookDetails> getBooksOfAuthor(String authorId) {
        return getAll().stream()
                .filter(ba -> ba.getAuthorId().equals(authorId))
                .map(ba -> bookService.getById(ba.getBookId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void unlink(String bookId, String authorId) {
        // șterge legătura specifică
        getAll().stream()
                .filter(ba -> ba.getBookId().equals(bookId) && ba.getAuthorId().equals(authorId))
                .map(BookAuthor::getId)
                .findFirst()
                .ifPresent(repo::delete);
    }
}

