package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "magazines")
public class MagazineDetails extends Publication {

    // 🔽 AICI poți pune câmpurile tale specifice revistei, de ex.:
    // private String issueNumber;
    // private String frequency;
    // etc.

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

    public MagazineDetails(String id, String title /*, alte câmpuri dacă ai */) {
        super(id, title);
        // setezi aici eventual celelalte câmpuri specifice revistei
    }

    // --- GET / SET ---

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    // dacă ai adăugat câmpuri specifice revistei (issueNumber etc.),
    // nu uita să adaugi și get/set pentru ele.
}
