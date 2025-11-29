package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(length = 50)
    private String id;

    @NotBlank(message = "Name is required.")
    @Size(max = 255, message = "Name must have at most 255 characters.")
    @Column(nullable = false)
    private String name;

    @Size(max = 255, message = "Nationality must have at most 255 characters.")
    @Column
    private String nationality;

    /**
     * Relația 1:N cu BookAuthor.
     * Un autor poate avea mai multe cărți (prin legături în BookAuthor).
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<BookAuthor> bookLinks = new ArrayList<>();

    /**
     * Relație 1:1 cu MagazineDetails.
     * Un autor are (cel mult) o singură revistă asociată.
     * Partea inversă este în MagazineDetails.author.
     */
    @OneToOne(mappedBy = "author")
    private MagazineDetails magazine;

    public Author() {}

    public Author(String id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }

    // --- GET / SET de bază ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public List<BookAuthor> getBookLinks() {
        return bookLinks;
    }

    public void setBookLinks(List<BookAuthor> bookLinks) {
        this.bookLinks = bookLinks;
    }

    public MagazineDetails getMagazine() {
        return magazine;
    }

    public void setMagazine(MagazineDetails magazine) {
        this.magazine = magazine;
    }

    // --- helperi opționali pentru relația cu BookAuthor ---

    public void addBookLink(BookAuthor link) {
        if (link == null) return;
        if (!bookLinks.contains(link)) {
            bookLinks.add(link);
            link.setAuthor(this);
        }
    }

    public void removeBookLink(BookAuthor link) {
        if (link == null) return;
        if (bookLinks.remove(link)) {
            link.setAuthor(null);
        }
    }
}
