package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.AuthorRepo;
import com.example.librarymanagementsystem.repository.BookAuthorRepo;
import com.example.librarymanagementsystem.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookAuthorService {

    private final BookAuthorRepo repo;
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    public BookAuthorService(BookAuthorRepo repo,
                             BookRepo bookRepo,
                             AuthorRepo authorRepo) {
        this.repo = repo;
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    // 🔹 listă pentru /bookauthors (index) — SORTATĂ după id BA1, BA2, ... BA10
    public List<BookAuthorRow> getAllRows() {
        return repo.findAllSorted().stream()
                .map(ba -> new BookAuthorRow(
                        ba.getId(),
                        ba.getBook().getId(),
                        ba.getBook().getTitle(),
                        ba.getAuthor().getId(),
                        ba.getAuthor().getName()
                ))
                .toList();
    }

    // 🔹 un singur "row" pentru pagina de details
    public BookAuthorRow getRowById(String id) {
        return repo.findById(id)
                .map(ba -> new BookAuthorRow(
                        ba.getId(),
                        ba.getBook().getId(),
                        ba.getBook().getTitle(),
                        ba.getAuthor().getId(),
                        ba.getAuthor().getName()
                ))
                .orElse(null);
    }

    // 🔹 pentru editForm / update
    public BookAuthor getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // 🔹 toți autorii unei cărți
    public List<Author> getAuthorsForBook(String bookId) {
        return repo.findByBook_Id(bookId)
                .stream()
                .map(BookAuthor::getAuthor)
                .toList();
    }

    // 🔹 toate cărțile unui autor
    public List<BookDetails> getBooksForAuthor(String authorId) {
        return repo.findByAuthor_Id(authorId)
                .stream()
                .map(BookAuthor::getBook)
                .toList();
    }

    // 🔹 CREATE – creează legătura carte–autor
    public void add(String id, String bookId, String authorId) {
        BookDetails book = bookRepo.findById(bookId).orElse(null);
        Author author = authorRepo.findById(authorId).orElse(null);

        if (book == null || author == null) {
            throw new IllegalArgumentException("Invalid book or author.");
        }

        if (repo.existsByBook_IdAndAuthor_Id(bookId, authorId)) {
            throw new IllegalArgumentException("This author is already linked to this book.");
        }

        if (repo.existsById(id)) {
            throw new IllegalArgumentException("A link with this ID already exists.");
        }

        BookAuthor link = new BookAuthor(id, book, author);
        repo.save(link);
    }

    // 🔹 UPDATE – modifică legătura carte–autor
    public void update(String id, String bookId, String authorId) {
        BookAuthor existing = repo.findById(id).orElse(null);
        if (existing == null) {
            throw new IllegalArgumentException("The link does not exist.");
        }

        BookDetails book = bookRepo.findById(bookId).orElse(null);
        Author author = authorRepo.findById(authorId).orElse(null);

        if (book == null || author == null) {
            throw new IllegalArgumentException("Invalid book or author.");
        }

        // dacă se schimbă perechea, verificăm să nu existe deja altă legătură cu aceeași pereche
        if (repo.existsByBook_IdAndAuthor_Id(bookId, authorId)) {
            // dacă e aceeași înregistrare, e ok; dacă e altă legătură, nu e ok
            if (!existing.getBook().getId().equals(bookId) ||
                    !existing.getAuthor().getId().equals(authorId)) {
                throw new IllegalArgumentException("This author is already linked to this book.");
            }
        }

        existing.setBook(book);
        existing.setAuthor(author);
        repo.save(existing);
    }

    // 🔹 DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // 🔹 generează ID-uri BA1, BA2, BA3...
    public String generateNextId() {
        int next = repo.findAll().stream()
                .map(BookAuthor::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.startsWith("BA"))
                .map(id -> id.substring(2))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;

        return "BA" + next;
    }

    // 🔹 pentru formularul /new – doar ID pre-generat
    public BookAuthor newForForm() {
        BookAuthor ba = new BookAuthor();
        ba.setId(generateNextId());
        return ba;
    }

    // 🔹 DTO folosit în index/details
    public static class BookAuthorRow {
        private String id;
        private String bookId;
        private String bookTitle;
        private String authorId;
        private String authorName;

        public BookAuthorRow(String id,
                             String bookId,
                             String bookTitle,
                             String authorId,
                             String authorName) {
            this.id = id;
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.authorId = authorId;
            this.authorName = authorName;
        }

        public String getId() {
            return id;
        }

        public String getBookId() {
            return bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getAuthorId() {
            return authorId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }
    }
}
