package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "readable_items")
public class ReadableItem {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required.")
    private String id;

    @NotBlank(message = "Publication ID is required.")
    @Pattern(
            regexp = "^PUB\\d{3}$",
            message = "Publication ID must have format PUB001 (PUB + 3 digits)."
    )
    @Column(name = "publication_id", nullable = false)
    private String publicationId;

    @NotBlank(message = "Barcode is required.")
    @Pattern(
            regexp = "^\\d{13}$",
            message = "Barcode must contain exactly 13 digits."
    )
    @Column(unique = true, nullable = false)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    /**
     * Relație N → 1 cu Library
     */
    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    /**
     * Relație N → 1 cu Loan
     * Mai multe iteme pot aparține aceluiași împrumut
     */
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    /**
     * Relație 1 → N cu Reservation
     * Un item poate avea mai multe rezervări
     */
    @OneToMany(mappedBy = "readableItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    // ENUM
    public enum ItemStatus {
        ACTIVE,
        CANCELLED,
        COMPLETED
    }

    // CONSTRUCTORS
    public ReadableItem() {}

    public ReadableItem(String id, String publicationId, String barcode, ItemStatus status) {
        this.id = id;
        this.publicationId = publicationId;
        this.barcode = barcode;
        this.status = status;
    }

    // GETTERS & SETTERS
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

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    // HELPER METHODS pentru relația cu Reservation
    public void addReservation(Reservation reservation) {
        if (reservation == null) return;
        if (!reservations.contains(reservation)) {
            reservations.add(reservation);
            reservation.setReadableItem(this);
        }
    }

    public void removeReservation(Reservation reservation) {
        if (reservation == null) return;
        if (reservations.remove(reservation)) {
            reservation.setReadableItem(null);
        }
    }
}
