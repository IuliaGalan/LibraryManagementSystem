package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(length = 50)
    private String id;

    @NotNull(message = "Reservation date is required.")
    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "readable_item_id", nullable = false)
    private ReadableItem readableItem;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public enum ReservationStatus {
        ACTIVE,
        COMPLETED,
        CANCELLED
    }

    // Constructors
    public Reservation() {}

    public Reservation(String id, LocalDate date, ReservationStatus status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public ReadableItem getReadableItem() { return readableItem; }
    public void setReadableItem(ReadableItem readableItem) { this.readableItem = readableItem; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }
}