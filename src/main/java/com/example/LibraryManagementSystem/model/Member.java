package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Member: aparține unei biblioteci (libraryId) și are împrumuturi + rezervări.
 *
 * Relații:
 *  - Library 1 → N Member
 *  - Member 1 → N Loan
 *  - Member 1 → N Reservation
 */
public class Member {

    private String id;
    private String name;
    private String libraryId;

    private List<Reservation> reservations;
    private List<Loan> loans;
    private String address;
    private String email; //de uitat peste

    // Constructor minim
    public Member(String id, String name, String libraryId) {
        this.id = id;
        this.name = name;
        this.libraryId = libraryId;
        this.reservations = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    public Member(){

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

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }



    public void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void addLoan(Loan loan) {
        if (loan != null && !loans.contains(loan)) {
            loans.add(loan);
        }
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }


}

