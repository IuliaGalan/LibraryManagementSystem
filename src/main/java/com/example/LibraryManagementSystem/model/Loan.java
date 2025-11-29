package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Loan: un împrumut efectuat de un membru al bibliotecii.
 *
 * Relații:
 *  - Loan N → 1 Member (un împrumut aparține unui membru)
 *  - Loan 1 → N ReadableItem (un împrumut poate conține mai multe iteme)
 */
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @Column(length = 50)
    private String id;

    /**
     * Relația N:1 cu Member.
     * Un împrumut este făcut de un singur membru.
     */
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Member is required.")
    private Member member;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date is required.")
    @Column(nullable = false)
    private Date date;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "due_date")
    private Date dueDate;

    @Column(length = 50)
    private String status;

    /**
     * Relația 1:N cu ReadableItem.
     * Un împrumut poate conține mai multe exemplare.
     */
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ReadableItem> items = new ArrayList<>();

    // Constructori
    public Loan() {}

    public Loan(String id, Member member, Date date) {
        this.id = id;
        this.member = member;
        this.date = date;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            item.setLoan(this);
        }
    }

    public void removeItem(ReadableItem item) {
        if (item == null) return;
        if (items.remove(item)) {
            item.setLoan(null);
        }
    }
}