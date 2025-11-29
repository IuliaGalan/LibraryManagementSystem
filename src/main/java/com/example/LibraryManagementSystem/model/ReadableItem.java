package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ReadableItem reprezintă un exemplar fizic (carte, revistă) din bibliotecă.
 *
 * Relații:
 *  - ReadableItem N → 1 Publication (un item aparține unei publicații)
 *  - ReadableItem N → 1 Library (un item se află într-o bibliotecă)
 *  - ReadableItem N → 1 Loan (un item poate fi împrumutat)
 *  - ReadableItem 1 → N Reservation (un item poate avea mai multe rezervări)
 */
@Entity
@Table(name = "readable_items")
public class ReadableItem {

    @Id
    @Column(length = 50)
    private String id;

    /**
     * Relația N:1 cu Publication.
     * Mai multe exemplare pot referi aceeași publicație (carte/revistă).
     */
    @ManyToOne
    @JoinColumn(name = "publication_id")
    @NotNull(message = "Publication is required.")
    private Publication publication;

    /**
     * Relația N:1 cu Library.
     * Un exemplar aparține unei biblioteci.
     */
    @ManyToOne
    @JoinColumn(name = "library_id")
    @NotNull(message = "Library is required.")
    private Library library;

    /**
     * Relația N:1 cu Loan.
     * Un exemplar poate fi asociat unui împrumut.
     */
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @NotBlank(message = "Barcode is required.")
    @Column(length = 100, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    /**
     * Relația 1:N cu Reservation.
     * Un item poate avea mai multe rezervări.
     */
    @OneToMany(mappedBy = "readableItem", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Reservation> reservations = new ArrayList<>();

    public enum Status {
        ACTIVE,
        CANCELLED,
        COMPLETED
    }

    // Constructori
    public ReadableItem() {}

    public ReadableItem(String id, Publication publication, Library library, String barcode, Status status) {
        this.id = id;
        this.publication = publication;
        this.library = library;
        this.barcode = barcode;
        this.status = status;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // Helper methods
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