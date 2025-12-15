package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.repository.ReservationRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepo repo;

    public ReservationService(ReservationRepo repo) {
        this.repo = repo;
    }

    public List<Reservation> getAll() {
        return repo.findAllSorted();
    }

    public List<Reservation> getAll(String sortBy, String direction,
                                    String filterMemberName,
                                    String filterStatus,
                                    String filterDate) {

        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        boolean hasMemberNameFilter = filterMemberName != null && !filterMemberName.trim().isEmpty();
        boolean hasStatusFilter = filterStatus != null && !filterStatus.trim().isEmpty();
        boolean hasDateFilter = filterDate != null && !filterDate.trim().isEmpty();

        Reservation.ReservationStatus status = null;
        if (hasStatusFilter) {
            try {
                status = Reservation.ReservationStatus.valueOf(filterStatus.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                hasStatusFilter = false;
            }
        }

        LocalDate date = null;
        if (hasDateFilter) {
            try {
                date = LocalDate.parse(filterDate.trim());
            } catch (Exception e) {
                hasDateFilter = false;
            }
        }

        if (hasMemberNameFilter && hasStatusFilter && hasDateFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndStatusAndDate(
                    filterMemberName.trim(), status, date, sort);
        }

        if (hasMemberNameFilter && hasStatusFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndStatus(
                    filterMemberName.trim(), status, sort);
        }

        if (hasMemberNameFilter && hasDateFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndDate(
                    filterMemberName.trim(), date, sort);
        }

        if (hasStatusFilter && hasDateFilter) {
            return repo.findByStatusAndDate(status, date, sort);
        }

        if (hasMemberNameFilter) {
            return repo.findByMember_NameContainingIgnoreCase(filterMemberName.trim(), sort);
        }

        if (hasStatusFilter) {
            return repo.findByStatus(status, sort);
        }

        if (hasDateFilter) {
            return repo.findByDate(date, sort);
        }

        return repo.findAll(sort);
    }

    public Reservation getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Reservation save(Reservation reservation) {
        return repo.save(reservation);
    }

    public void add(String id, Reservation reservation) {
        reservation.setId(id);
        repo.save(reservation);
    }

    public void update(String id, Reservation reservation) {
        reservation.setId(id);
        repo.save(reservation);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Reservation::getId)
                .filter(id -> id != null && id.toUpperCase().startsWith("R"))
                .map(id -> {
                    String numericPart = id.substring(1);
                    try {
                        return Integer.parseInt(numericPart);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0);

        return "R" + (maxNumber + 1);
    }

    public Reservation newForForm() {
        Reservation res = new Reservation();
        res.setId(generateNextId());
        return res;
    }
}