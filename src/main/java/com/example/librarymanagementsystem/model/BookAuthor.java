package com.example.librarymanagementsystem.model;

public class BookAuthor {
    private String id;
    private String bookId;
    private String authorId;

    public BookAuthor() {} // necesar pt formular (new BookAuthor())

    public BookAuthor(String id, String bookId, String authorId) {
        this.id = id;
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public String getId() {
        return id;
    }
    public String getBookId() {
        return bookId;
    }
    public String getAuthorId() {
        return authorId;
    }

    public void setId(String id) { this.id = id; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
}