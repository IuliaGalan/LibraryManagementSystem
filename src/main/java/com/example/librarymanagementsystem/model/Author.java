package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

//Relația M:N: un autor poate scrie mai multe carti

public class Author {
    private String id;
    private String name;
    private List<BookDetails> books;
    private String nationality;

    //Spring are nevoie de un constructor gol cand primeste date din formular
    public Author() {
        this.books = new ArrayList<>();
    }
    //pt crearea manuala a obiectului
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
    public void setId(String id) { this.id = id; }
    public void setNationality(String nationality) { this.nationality = nationality; }


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