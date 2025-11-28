package com.example.librarymanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Publication {

    @Id
    @Column(name = "id", length = 50, nullable = false, unique = true)
    protected String id;

    @Column(name = "title", nullable = false)
    protected String title;

    public Publication() {}

    public Publication(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
