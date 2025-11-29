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

    // READ - toate rezervările
    public List<Reservation> getAll() {
        return repo.findAll();
    }

    // READ - o rezervare după ID
    public Reservation getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE & UPDATE
    public Reservation save(Reservation reservation) {
        return repo.save(reservation);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // Generare ID automat
    public String generateNextId() {
        return "R" + (repo.count() + 1);
    }

    // Helper pentru formular nou
    public Reservation newForForm() {
        Reservation reservation = new Reservation();
        reservation.setId(generateNextId());
        return reservation;
    }

    // --- Metode pentru relații ---

    // Găsește toate rezervările unui membru
    public List<Reservation> getReservationsByMember(String memberId) {
        return repo.findByMember_Id(memberId);
    }

    // Găsește toate rezervările pentru un item
    public List<Reservation> getReservationsByItem(String readableItemId) {
        return repo.findByReadableItem_Id(readableItemId);
    }

    // Găsește rezervările după status
    public List<Reservation> getReservationsByStatus(String status) {
        return repo.findByStatus(status);
    }

    // Găsește rezervările unui membru cu un anumit status
    public List<Reservation> getReservationsByMemberAndStatus(String memberId, String status) {
        return repo.findByMember_IdAndStatus(memberId, status);
    }

    public void add(String id, Reservation reservation) {
    }
}
