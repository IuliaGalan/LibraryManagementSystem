package com.example.librarymanagementsystem.model;

public class MagazineDetails extends Publication {
    private String publisher;
    private String language;

    public MagazineDetails() {      // ← necesar pentru new MagazineDetails()
        super();
    }

    public MagazineDetails(String id, String title, String publisher, String language) {
        super(id, title);
        this.publisher = publisher;
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}