package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Loan reprezintă un împrumut efectuat de un membru.
 *
 * Relații JPA:
 *  - Loan N → 1 Member (un împrumut aparține unui membru)
 *  - Loan 1 → N ReadableItem (un împrumut poate conține mai multe iteme)
 *  - Loan 1 → N Reservation (un împrumut poate avea rezervări asociate)
 */
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @Column(length = 50)
    private String id;

    @NotNull
    @Column(name = "loan_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate loanDate;

    @NotNull
    @Column(name = "due_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    /**
     * Relație N → 1 cu Member
     * Mai multe împrumuturi pot aparține aceluiași membru
     */
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * Relație 1 → N cu ReadableItem
     * Un împrumut poate conține mai multe iteme
     * mappedBy="loan" înseamnă că ReadableItem.loan este owner-ul relației
     */
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadableItem> items = new ArrayList<>();

    /**
     * Relație 1 → N cu Reservation
     * Un împrumut poate avea mai multe rezervări
     */
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    // ENUM pentru status
    public enum LoanStatus {
        OPEN,
        CLOSED,
        OVERDUE
    }

    // CONSTRUCTORS
    public Loan() {}

    public Loan(String id, LocalDate loanDate, LocalDate dueDate, LoanStatus status) {
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // GETTERS & SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public List<ReadableItem> getItems() { return items; }
    public void setItems(List<ReadableItem> items) { this.items = items; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    // HELPER METHODS pentru relația cu ReadableItem
    public void addItem(ReadableItem item) {
        if (item == null) return;
        if (!items.contains(item)) {
            items.add(item);
            item.setLoan(this);
        }
    }

    public void removeItem(ReadableItem item) {
        if (item == null) return;
        if (items.remove(item)) {
            item.setLoan(null);
        }
    }

    // HELPER METHODS pentru relația cu Reservation
    public void addReservation(Reservation reservation) {
        if (reservation == null) return;
        if (!reservations.contains(reservation)) {
            reservations.add(reservation);
            reservation.setLoan(this);
        }
    }

    public void removeReservation(Reservation reservation) {
        if (reservation == null) return;
        if (reservations.remove(reservation)) {
            reservation.setLoan(null);
        }
    }
}