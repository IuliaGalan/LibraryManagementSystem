package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clasa Loan reprezintă un împrumut efectuat de un membru al bibliotecii.
 *
 * Relații:
 *  - Library 1 → N Loan  (o bibliotecă are mai multe împrumuturi)
 *  - Member 1 → N Loan   (un membru poate avea mai multe împrumuturi)
 *  - Loan 1 → N ReadableItem (un împrumut poate conține mai multe iteme)
 *  - Loan 1 → N Reservation  (un împrumut poate fi asociat cu una sau mai multe rezervări)
 */
public class Loan {

    private String id;                  // identificatorul împrumutului
    private String memberId;            // ID-ul membrului care a împrumutat
    private String date;                // data împrumutului (ca text simplu, ex: "2025-10-27")

    private List<Reservation> reservations;   // rezervările asociate acestui împrumut
    private List<ReadableItem> items;         // itemele împrumutate (cărți, reviste etc.)

    public Loan(String id, String memberId, String date) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.reservations = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    // --- Getteri și setteri ---
    public String getMemberId() {
        return memberId;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<ReadableItem> getItems() {
        return items;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setItems(List<ReadableItem> items) {
        this.items = items;
    }

    //metode
    public void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void addItem(ReadableItem item) {
        if (item != null && !items.contains(item)) {
            items.add(item);
        }
    }

    public void removeItem(ReadableItem item) {
        items.remove(item);
    }
}

