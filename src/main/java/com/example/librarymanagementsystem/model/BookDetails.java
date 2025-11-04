package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

//Relația M:N: o carte poate avea mai mulți autori

public class BookDetails extends Publication {
    private List<Author> bookAuthors;
    private String genre;

    public BookDetails() {
        super(); // cheamă Publication() gol
        this.bookAuthors = new ArrayList<>();
    }

    public BookDetails(String id, String title, String genre) {
        super(id, title);
        this.bookAuthors = new ArrayList<>();
        this.genre = genre;
    }

    public List<Author> getAuthors() {
        return bookAuthors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    //legatura bidirectionala
    public void addAuthor(Author author) {
        if (!bookAuthors.contains(author)) {
            bookAuthors.add(author);
            author.addBook(this);
        }
    }

    public void removeAuthor(Author author) {
        if (bookAuthors.contains(author)) {
            bookAuthors.remove(author);
            author.getBooks().remove(this);
        }
    }
}