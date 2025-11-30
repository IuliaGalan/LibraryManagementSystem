package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, String> {

    // Sortare naturală: R1, R2, R3, ..., R10, R11
    @Query("SELECT r FROM Reservation r ORDER BY CAST(SUBSTRING(r.id, 2) AS int)")
    List<Reservation> findAllSorted();
}