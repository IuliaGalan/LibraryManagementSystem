package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class BookDetails extends Publication {
    private List<Author> bookAuthors;   // trebuie să existe, exact ca în JSON
    private String genre;

    public BookDetails() {
        super();
        this.bookAuthors = new ArrayList<>();
    }

    public BookDetails(String id, String title, String genre) {
        super(id, title);
        this.bookAuthors = new ArrayList<>();
        this.genre = genre;
    }

    // === getter / setter pentru compatibilitate cu JSON ===
    public List<Author> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(List<Author> bookAuthors) {
        this.bookAuthors = (bookAuthors != null) ? bookAuthors : new ArrayList<>();
    }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    // === metodele cerute de codul tău existent ===
    // alias util dacă în alte locuri folosești "getAuthors"
    public List<Author> getAuthors() { return bookAuthors; }

    public void addAuthor(Author author) {
        if (author == null) return;
        if (this.bookAuthors == null) this.bookAuthors = new ArrayList<>();
        if (!this.bookAuthors.contains(author)) {
            this.bookAuthors.add(author);
            // legătură bidirecțională – presupune că ai Author.addBook(BookDetails)
            author.addBook(this);
        }
    }

    public void removeAuthor(Author author) {
        if (author == null || this.bookAuthors == null) return;
        if (this.bookAuthors.remove(author)) {
            // rupem legătura bidirecțională – presupune că ai Author.getBooks()
            if (author.getBooks() != null) {
                author.getBooks().remove(this);
            }
        }
    }
}
