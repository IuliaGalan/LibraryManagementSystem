package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Reservation reprezintă o rezervare efectuată de un membru pentru un ReadableItem.
 *
 * Relații JPA:
 *  - Reservation N → 1 Member (mai multe rezervări pot aparține aceluiași membru)
 *  - Reservation N → 1 ReadableItem (mai multe rezervări pot fi pentru același item)
 *  - Reservation N → 1 Loan (opțional - o rezervare poate fi asociată cu un împrumut)
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(length = 50)
    private String id;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    /**
     * Relație N → 1 cu Member
     * Mai multe rezervări pot aparține aceluiași membru
     */
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * Relație N → 1 cu ReadableItem
     * Mai multe rezervări pot fi pentru același item
     */
    @ManyToOne
    @JoinColumn(name = "readable_item_id", nullable = false)
    private ReadableItem readableItem;

    /**
     * Relație N → 1 cu Loan (opțional)
     * O rezervare poate fi asociată cu un împrumut
     */
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    // ENUM pentru status
    public enum ReservationStatus {
        ACTIVE,
        COMPLETED,
        CANCELLED
    }

    // CONSTRUCTORS
    public Reservation() {}

    public Reservation(String id, LocalDate date, ReservationStatus status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    // GETTERS & SETTERS
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