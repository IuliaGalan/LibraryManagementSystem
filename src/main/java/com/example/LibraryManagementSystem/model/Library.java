package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Library - reprezintă o bibliotecă ce conține membri și publicații
 *
 * Relații:
 *  - Library 1 → N ReadableItem (o bibliotecă deține mai multe exemplare)
 */
@Entity
@Table(name = "libraries")
public class Library {

    @Id
    @Column(length = 50)
    private String id;

    @NotBlank(message = "Name is required.")
    @Size(max = 255, message = "Name must have at most 255 characters.")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Address must have at most 500 characters.")
    @Column(length = 500)
    private String address;

    @Size(max = 20, message = "Phone number must have at most 20 characters.")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Email(message = "Invalid email format.")
    @Size(max = 255)
    @Column(length = 255)
    private String email;

    /**
     * Relația 1:N cu ReadableItem.
     * O bibliotecă poate avea mai multe exemplare de publicații.
     */
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReadableItem> items = new ArrayList<>();

    // Constructori
    public Library() {}

    public Library(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReadableItem> getItems() {
        return items;
    }

    public void setItems(List<ReadableItem> items) {
        this.items = items;
    }

    // Helper methods
    public void addItem(ReadableItem item) {
        if (item == null) return;
        if (!items.contains(item)) {
            items.add(item);
            item.setLibrary(this);
        }
    }

    public void removeItem(ReadableItem item) {
        if (item == null) return;
        if (items.remove(item)) {
            item.setLibrary(null);
        }
    }
}