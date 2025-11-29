package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, String> {

    // Găsește toate rezervările unui membru
    List<Reservation> findByMember_Id(String memberId);

    // Găsește toate rezervările pentru un item
    List<Reservation> findByReadableItem_Id(String readableItemId);

    // Găsește rezervările după status
    List<Reservation> findByStatus(String status);

    // Găsește rezervările unui membru cu un anumit status
    List<Reservation> findByMember_IdAndStatus(String memberId, String status);
}