package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Member: aparține unei biblioteci și are împrumuturi + rezervări.
 *
 * Relații:
 *  - Member 1 → N Loan (un membru poate avea mai multe împrumuturi)
 *  - Member 1 → N Reservation (un membru poate avea mai multe rezervări)
 */
@Entity
@Table(name = "members")
public class Member {

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

    @Email(message = "Invalid email format.")
    @Size(max = 255)
    @Column(length = 255)
    private String email;

    /**
     * Relația 1:N cu Loan.
     * Un membru poate avea mai multe împrumuturi.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Loan> loans = new ArrayList<>();

    /**
     * Relația 1:N cu Reservation.
     * Un membru poate avea mai multe rezervări.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Reservation> reservations = new ArrayList<>();

    // Constructori
    public Member() {}

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // Helper methods
    public void addLoan(Loan loan) {
        if (loan == null) return;
        if (!loans.contains(loan)) {
            loans.add(loan);
            loan.setMember(this);
        }
    }

    public void removeLoan(Loan loan) {
        if (loan == null) return;
        if (loans.remove(loan)) {
            loan.setMember(null);
        }
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) return;
        if (!reservations.contains(reservation)) {
            reservations.add(reservation);
            reservation.setMember(this);
        }
    }

    public void removeReservation(Reservation reservation) {
        if (reservation == null) return;
        if (reservations.remove(reservation)) {
            reservation.setMember(null);
        }
    }
}