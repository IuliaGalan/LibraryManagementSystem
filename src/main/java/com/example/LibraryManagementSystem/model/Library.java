package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libraries")
public class Library {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required.")
    private String id;

    @NotBlank(message = "Name is required.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Address is required.")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadableItem> readableItems = new ArrayList<>();

    // Constructors
    public Library() {}

    public Library(String id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }

    public List<ReadableItem> getReadableItems() { return readableItems; }
    public void setReadableItems(List<ReadableItem> readableItems) { this.readableItems = readableItems; }

    // Helper methods
    public void addMember(Member member) {
        if (member == null) return;
        if (!members.contains(member)) {
            members.add(member);
            member.setLibrary(this);
        }
    }

    public void removeMember(Member member) {
        if (member == null) return;
        if (members.remove(member)) {
            member.setLibrary(null);
        }
    }

    public void addReadableItem(ReadableItem item) {
        if (item == null) return;
        if (!readableItems.contains(item)) {
            readableItems.add(item);
            item.setLibrary(this);
        }
    }

    public void removeReadableItem(ReadableItem item) {
        if (item == null) return;
        if (readableItems.remove(item)) {
            item.setLibrary(null);
        }
    }
}