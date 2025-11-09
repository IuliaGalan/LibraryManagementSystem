package com.example.librarymanagementsystem.model;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 * Clasa Loan e un imprumut efectuat de un membru al bibliotecii
 *
 * Relații:
 *  - Loan 1 → N ReadableItem (un împrumut poate conține mai multe iteme)
 *  - Loan 1 → N Reservation  (un împrumut poate fi asociat cu una sau mai multe rezervări)
 */
public class Loan {

    private String id;
    private String memberId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private List<Reservation> reservations;
    private List<ReadableItem> items;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private String status;

    public Loan(String id, String memberId, Date date) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.reservations = new ArrayList<>();
        this.items = new ArrayList<>();
    }
    public Loan(){
    }


    // getter setter

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

    public String getMemberId() {
        return memberId;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
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

