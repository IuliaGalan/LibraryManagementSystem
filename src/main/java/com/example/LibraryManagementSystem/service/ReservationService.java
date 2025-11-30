package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.repository.ReservationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepo repo;

    public ReservationService(ReservationRepo repo) {
        this.repo = repo;
    }

    // LIST cu sortare naturală
    public List<Reservation> getAll() {
        return repo.findAllSorted();
    }

    // GET BY ID
    public Reservation getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE
    public Reservation save(Reservation reservation) {
        return repo.save(reservation);
    }

    // CREATE cu ID explicit
    public void add(String id, Reservation reservation) {
        reservation.setId(id);
        repo.save(reservation);
    }

    // UPDATE
    public void update(String id, Reservation reservation) {
        reservation.setId(id);
        repo.save(reservation);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // GENERATE NEXT ID
    public String generateNextId() {
        return "R" + (repo.count() + 1);
    }

    // FOR FORM
    public Reservation newForForm() {
        Reservation res = new Reservation();
        res.setId(generateNextId());
        return res;
    }
}