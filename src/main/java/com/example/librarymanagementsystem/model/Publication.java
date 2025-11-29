package com.example.librarymanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Publication {

    @Id
    @Column(name = "id", length = 50, nullable = false, unique = true)
    @NotBlank(message = "ID is required.")
    @Size(max = 50, message = "ID must have at most 50 characters.")
    protected String id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title must have at most 255 characters.")
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
