package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

//Relația M:N: un autor poate scrie mai multe carti

public class Author {
    private String id;
    private String name;
    private List<BookDetails> books;
    private String nationality;

    public Author(String id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.books = new ArrayList<>();
    }




    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BookDetails> getBooks() {
        return books;
    }

    public String getNationality() {
        return nationality;
    }

    public void setName(String name) {
        this.name = name;
    }

    //relație bidirectionala
    public void addBook(BookDetails book) {
        if (!books.contains(book)) {
            books.add(book);
            book.addAuthor(this);
        }
    }

    public void removeBook(BookDetails book) {
        if (books.contains(book)) {
            books.remove(book);
            book.removeAuthor(this);
        }
    }
}