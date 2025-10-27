package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

//Relația M:N: o carte poate avea mai mulți autori

public class BookDetails extends Publication {
    private List<Author> bookAuthors;

    public BookDetails(String id, String title) {
        super(id, title);
        this.bookAuthors = new ArrayList<>();
    }

    public List<Author> getAuthors() {
        return bookAuthors;
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