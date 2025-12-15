package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Reservation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, String> {

    // ✅ SORTARE NATURALĂ CORECTĂ pentru R1, R2, ... R9, R10, R11
    @Query("SELECT r FROM Reservation r ORDER BY LENGTH(r.id), r.id")
    List<Reservation> findAllSorted();

    List<Reservation> findAll(Sort sort);

    List<Reservation> findByMember_NameContainingIgnoreCase(String memberName, Sort sort);
    List<Reservation> findByStatus(Reservation.ReservationStatus status, Sort sort);
    List<Reservation> findByDate(LocalDate date, Sort sort);

    List<Reservation> findByMember_NameContainingIgnoreCaseAndStatus(
            String memberName, Reservation.ReservationStatus status, Sort sort);
    List<Reservation> findByMember_NameContainingIgnoreCaseAndDate(
            String memberName, LocalDate date, Sort sort);
    List<Reservation> findByStatusAndDate(
            Reservation.ReservationStatus status, LocalDate date, Sort sort);

    List<Reservation> findByMember_NameContainingIgnoreCaseAndStatusAndDate(
            String memberName, Reservation.ReservationStatus status, LocalDate date, Sort sort);
}
