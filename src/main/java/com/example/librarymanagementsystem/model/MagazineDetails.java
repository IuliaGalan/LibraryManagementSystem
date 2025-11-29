package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "magazines")
public class MagazineDetails extends Publication {

    // editorul / casa de editură a revistei
    @NotBlank(message = "Publisher ist erforderlich.")
    @Column(name = "publisher")
    private String publisher;

    // limba revistei (ex: EN, RO, DE)
    @NotBlank(message = "Sprache ist erforderlich.")
    @Column(name = "language")
    private String language;

    /**
     * Relație 1:1 cu Author.
     * O revistă are un singur autor.
     * Coloana author_id apare în tabelul magazines.
     */
    @OneToOne
    @JoinColumn(name = "author_id", unique = true)
    private Author author;

    public MagazineDetails() {
        super();
    }

    public MagazineDetails(String id, String title, String publisher, String language) {
        super(id, title);
        this.publisher = publisher;
        this.language = language;
    }

    // --- GET / SET ---

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
