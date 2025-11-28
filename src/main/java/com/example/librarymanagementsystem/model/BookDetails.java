package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "books")
public class BookDetails extends Publication {

    @NotBlank(message = "Genul este obligatoriu.")
    @Column(name = "genre")
    private String genre;

    public BookDetails() {
        super();
    }

    public BookDetails(String id, String title, String genre) {
        super(id, title);
        this.genre = genre;
    }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}
