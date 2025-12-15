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

    // ✅ METODĂ VECHE (compatibilitate)
    public List<Reservation> getAll() {
        return repo.findAllSorted();
    }

    // ✅ METODĂ NOUĂ - CU SORTARE ȘI FILTRARE
    public List<Reservation> getAll(String sortBy, String direction,
                                    String filterMemberName,
                                    String filterStatus,
                                    String filterDate) {

        // 1️⃣ CONSTRUIEȘTE SORTAREA
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // 2️⃣ VERIFICĂ CARE FILTRE SUNT ACTIVE
        boolean hasMemberNameFilter = filterMemberName != null && !filterMemberName.trim().isEmpty();
        boolean hasStatusFilter = filterStatus != null && !filterStatus.trim().isEmpty();
        boolean hasDateFilter = filterDate != null && !filterDate.trim().isEmpty();

        // Convert status string to enum if needed
        Reservation.ReservationStatus status = null;
        if (hasStatusFilter) {
            try {
                status = Reservation.ReservationStatus.valueOf(filterStatus.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                hasStatusFilter = false;
            }
        }

        // Convert date string to LocalDate if needed
        LocalDate date = null;
        if (hasDateFilter) {
            try {
                date = LocalDate.parse(filterDate.trim());
            } catch (Exception e) {
                hasDateFilter = false;
            }
        }

        // 3️⃣ APLICĂ FILTRELE CORESPUNZĂTOARE

        // TOATE 3 FILTRE
        if (hasMemberNameFilter && hasStatusFilter && hasDateFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndStatusAndDate(
                    filterMemberName.trim(), status, date, sort);
        }

        // 2 FILTRE: Member + Status
        if (hasMemberNameFilter && hasStatusFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndStatus(
                    filterMemberName.trim(), status, sort);
        }

        // 2 FILTRE: Member + Date
        if (hasMemberNameFilter && hasDateFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndDate(
                    filterMemberName.trim(), date, sort);
        }

        // 2 FILTRE: Status + Date
        if (hasStatusFilter && hasDateFilter) {
            return repo.findByStatusAndDate(status, date, sort);
        }

        // 1 FILTRU: Doar Member Name
        if (hasMemberNameFilter) {
            return repo.findByMember_NameContainingIgnoreCase(filterMemberName.trim(), sort);
        }

        // 1 FILTRU: Doar Status
        if (hasStatusFilter) {
            return repo.findByStatus(status, sort);
        }

        // 1 FILTRU: Doar Date
        if (hasDateFilter) {
            return repo.findByDate(date, sort);
        }

        // FĂRĂ FILTRE: Doar sortare
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
                .filter(id -> id != null && id.startsWith("R"))
                .map(id -> id.substring(1))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "R" + (maxNumber + 1);
    }

    public Reservation newForForm() {
        Reservation res = new Reservation();
        res.setId(generateNextId());
        return res;
    }
}