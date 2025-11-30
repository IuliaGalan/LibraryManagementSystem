package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "readable_items")
public class ReadableItem {

    @Id
    @Column(length = 50)
    private String id;

    @NotBlank
    @Column(name = "publication_id", nullable = false)
    private String publicationId;  // ✅ String, NU relație JPA

    @Column(unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    // Constructori
    public ReadableItem() {}

    public ReadableItem(String id, String publicationId, String barcode, ItemStatus status) {
        this.id = id;
        this.publicationId = publicationId;
        this.barcode = barcode;
        this.status = status;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPublicationId() { return publicationId; }
    public void setPublicationId(String publicationId) { this.publicationId = publicationId; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public ItemStatus getStatus() { return status; }
    public void setStatus(ItemStatus status) { this.status = status; }

    public Library getLibrary() { return library; }
    public void setLibrary(Library library) { this.library = library; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    // Enum pentru status
    public enum ItemStatus {
        ACTIVE, CANCELLED, COMPLETED
    }
}