package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;
//* Relații:
// *  - Library 1 → N Member (o bibliotecă are mai mulți membri)
// *  - Library 1 → N ReadableItem (o bibliotecă deține mai multe exemplare de publicații)
// */
public class Library {

    private String id;
    private String name;
    private String address;

    private List<Author> authors;
    private List<Publication> publications;
    private List<BookDetails> books;
    private List<MagazineDetails> magazines;
    private String phoneNumber;
    private String email;

    public Library(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;

        this.authors = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.books = new ArrayList<>();
        this.magazines = new ArrayList<>();
    }

    //getter si setter

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getAddress() {
        return address;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public List<BookDetails> getBooks() {
        return books;
    }

    public List<MagazineDetails> getMagazines() {
        return magazines;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public void setBooks(List<BookDetails> books) {
        this.books = books;
    }

    public void setMagazines(List<MagazineDetails> magazines) {
        this.magazines = magazines;
    }

    //  metode pentru adaugare/ștergere
    public void addAuthor(Author author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
        }
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public void addPublication(Publication publication) {
        if (publication != null && !publications.contains(publication)) {
            publications.add(publication);
        }
    }

    public void removePublication(Publication publication) {
        publications.remove(publication);
    }

    public void addBook(BookDetails book) {
        if (book != null && !books.contains(book)) {
            books.add(book);
        }
    }

    public void removeBook(BookDetails book) {
        books.remove(book);
    }

    public void addMagazine(MagazineDetails magazine) {
        if (magazine != null && !magazines.contains(magazine)) {
            magazines.add(magazine);
        }
    }

    public void removeMagazine(MagazineDetails magazine) {
        magazines.remove(magazine);
    }
}