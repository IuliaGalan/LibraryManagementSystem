package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookAuthor;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.RepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookAuthorService extends BaseService<BookAuthor> {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookAuthorService(RepositoryInterface<BookAuthor> repo,
                             BookService bookService,
                             AuthorService authorService) {
        super(repo);
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // Generează ID-uri: BA1, BA2, BA3, ...
    public String generateNextId() {
        int next = getAll().stream()
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

    public BookAuthor newForForm() {
        BookAuthor ba = new BookAuthor();
        ba.setId(generateNextId());
        return ba;
    }

    // DTO pentru tabelul BookAuthor
    public static class BookAuthorRow {
        public String id;
        public String bookId;
        public String bookTitle;
        public String authorId;
        public String authorName;
    }

    // Rânduri pentru index.html (BookAuthor)
    public List<BookAuthorRow> getAllRows() {
        return getAll().stream().map(link -> {
            BookAuthorRow r = new BookAuthorRow();
            r.id = link.getId();
            r.bookId = link.getBookId();
            r.authorId = link.getAuthorId();

            BookDetails b = bookService.getById(r.bookId);
            Author a = authorService.getById(r.authorId);

            r.bookTitle = (b != null ? b.getTitle() : "(unknown)");
            r.authorName = (a != null ? a.getName() : "(unknown)");

            return r;
        }).toList();
    }

    public BookAuthorRow getRowById(String id) {
        BookAuthor link = getById(id);
        if (link == null) return null;

        BookAuthorRow r = new BookAuthorRow();
        r.id = link.getId();
        r.bookId = link.getBookId();
        r.authorId = link.getAuthorId();

        BookDetails b = bookService.getById(r.bookId);
        Author a = authorService.getById(r.authorId);

        r.bookTitle = (b != null ? b.getTitle() : "(unknown)");
        r.authorName = (a != null ? a.getName() : "(unknown)");

        return r;
    }

    // 🔹 Toate cărțile pentru un autor (folosit la Author details)
    public List<BookDetails> getBooksForAuthor(String authorId) {
        return getAll().stream()
                .filter(link -> Objects.equals(authorId, link.getAuthorId()))
                .map(link -> bookService.getById(link.getBookId()))
                .filter(Objects::nonNull)
                .toList();
    }

    // 🔹 Toți autorii pentru o carte (folosit la Book details)
    public List<Author> getAuthorsForBook(String bookId) {
        return getAll().stream()
                .filter(link -> Objects.equals(bookId, link.getBookId()))
                .map(link -> authorService.getById(link.getAuthorId()))
                .filter(Objects::nonNull)
                .toList();
    }
}
