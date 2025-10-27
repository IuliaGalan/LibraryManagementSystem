package com.example.librarymanagementsystem.model;

public class MagazineDetails extends Publication {
    private String publisher;

    public MagazineDetails(String id, String title, String publisher) {
        super(id, title);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}