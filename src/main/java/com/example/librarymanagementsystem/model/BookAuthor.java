package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "book_authors")
public class BookAuthor {

    @Id
    @Column(name = "id", length = 50)
    @NotBlank(message = "ID is required.")
    private String id;

    /**
     * Multe legături BookAuthor pot referi aceeași carte.
     * Coloana FK în DB va fi book_id.
     */
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book is required.")
    private BookDetails book;

    /**
     * Multe legături BookAuthor pot referi același autor.
     * Coloana FK în DB va fi author_id.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "Author is required.")
    private Author author;

    public BookAuthor() {}

    public BookAuthor(String id, BookDetails book, Author author) {
        this.id = id;
        this.book = book;
        this.author = author;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public BookDetails getBook() { return book; }
    public void setBook(BookDetails book) { this.book = book; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}
