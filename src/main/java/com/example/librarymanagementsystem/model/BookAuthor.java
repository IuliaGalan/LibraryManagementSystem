package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_authors")
public class BookAuthor {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "book_id", nullable = false)
    private String bookId;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    public BookAuthor() {}

    public BookAuthor(String id, String bookId, String authorId) {
        this.id = id;
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
}
