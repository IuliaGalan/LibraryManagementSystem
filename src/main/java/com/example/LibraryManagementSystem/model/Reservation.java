package com.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Reservation: o rezervare efectuată de un membru pentru un exemplar.
 *
 * Relații:
 *  - Reservation N → 1 Member (o rezervare aparține unui membru)
 *  - Reservation N → 1 ReadableItem (o rezervare se referă la un item)
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(length = 50)
    private String id;

    /**
     * Relația N:1 cu Member.
     * O rezervare este făcută de un singur membru.
     */
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Member is required.")
    private Member member;

    /**
     * Relația N:1 cu ReadableItem.
     * O rezervare se referă la un singur exemplar.
     */
    @ManyToOne
    @JoinColumn(name = "readable_item_id", nullable = false)
    @NotNull(message = "Readable item is required.")
    private ReadableItem readableItem;

    @NotBlank(message = "Date is required.")
    @Column(length = 50)
    private String date;

    @Column(length = 50)
    private String status;

    // Constructori
    public Reservation() {}

    public Reservation(String id, Member member, ReadableItem readableItem, String date, String status) {
        this.id = id;
        this.member = member;
        this.readableItem = readableItem;
        this.date = date;
        this.status = status;
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

    public ReadableItem getReadableItem() {
        return readableItem;
    }

    public void setReadableItem(ReadableItem readableItem) {
        this.readableItem = readableItem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}