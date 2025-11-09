package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.RepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookAuthorService extends BaseService<BookAuthor> {

    private final AuthorService authorService;
    private final BookService bookService;

    // Spring va injecta bean-ul concret (BookAuthorRepo) pentru acest tip generic
    public BookAuthorService(RepositoryInterface<BookAuthor> repo,
                             AuthorService authorService,
                             BookService bookService) {
        super(repo);
        this.authorService = authorService;
        this.bookService = bookService;
    }

    public boolean link(String bookId, String authorId) {
        boolean exists = getAll().stream()
                .anyMatch(ba -> ba.getBookId().equals(bookId) && ba.getAuthorId().equals(authorId));
        if (exists) return false;

        String linkId = UUID.randomUUID().toString();
        repo.save(linkId, new BookAuthor(linkId, bookId, authorId));
        return true;
    }

    public boolean unlink(String bookId, String authorId) {
        Optional<String> idOpt = getAll().stream()
                .filter(ba -> ba.getBookId().equals(bookId) && ba.getAuthorId().equals(authorId))
                .map(BookAuthor::getId)
                .findFirst();
        idOpt.ifPresent(repo::delete);
        return idOpt.isPresent();
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
}
