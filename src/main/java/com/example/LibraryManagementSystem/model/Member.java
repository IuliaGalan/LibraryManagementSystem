package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required.")
    private String id;

    @NotBlank(message = "Name is required.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Address is required.")
    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    // Constructors
    public Member() {}

    public Member(String id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Library getLibrary() { return library; }
    public void setLibrary(Library library) { this.library = library; }

    public List<Loan> getLoans() { return loans; }
    public void setLoans(List<Loan> loans) { this.loans = loans; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

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