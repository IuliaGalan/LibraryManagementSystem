package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class BookDetails extends Publication {

    @NotBlank(message = "Genre is required.")
    @Size(max = 255, message = "Genre must have at most 255 characters.")
    @Column(name = "genre")
    private String genre;

    /**
     * Relația 1:N cu BookAuthor.
     * O carte poate avea mai multe legături cu autori.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<BookAuthor> authorLinks = new ArrayList<>();

    public BookDetails() {
        super();
    }

    public BookDetails(String id, String title, String genre) {
        super(id, title);
        this.genre = genre;
    }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public List<BookAuthor> getAuthorLinks() {
        return authorLinks;
    }

    public void setAuthorLinks(List<BookAuthor> authorLinks) {
        this.authorLinks = authorLinks;
    }

    // helper optional
    public void addAuthorLink(BookAuthor link) {
        if (link == null) return;
        if (!authorLinks.contains(link)) {
            authorLinks.add(link);
            link.setBook(this);
        }
    }

    public void removeAuthorLink(BookAuthor link) {
        if (link == null) return;
        if (authorLinks.remove(link)) {
            link.setBook(null);
        }
    }
}
