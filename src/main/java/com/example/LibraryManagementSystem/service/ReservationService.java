package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.repository.ReservationRepo;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends BaseService<Reservation> {

    public ReservationService(ReservationRepo repo) {
        super(repo);
    }
}
