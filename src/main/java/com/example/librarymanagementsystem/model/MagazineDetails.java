package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "magazines")
public class MagazineDetails extends Publication {

    @Column
    private String publisher;

    @Column
    private String language;

    public MagazineDetails() {
        super();
    }

    public MagazineDetails(String id, String title, String publisher, String language) {
        super(id, title);
        this.publisher = publisher;
        this.language = language;
    }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
