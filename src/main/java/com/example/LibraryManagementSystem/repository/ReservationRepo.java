package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Reservation ;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepo extends InFileRepository<Reservation> {
    public ReservationRepo() {
        super("src/main/resources/data/reservation.json",
                new TypeReference<java.util.List<Reservation>>() {
                });
    }
}