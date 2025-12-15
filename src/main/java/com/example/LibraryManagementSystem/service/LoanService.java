package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Loan;
import com.example.librarymanagementsystem.repository.LoanRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepo repo;

    public LoanService(LoanRepo repo) {
        this.repo = repo;
    }

    // ✅ METODĂ VECHE (compatibilitate)
    public List<Loan> getAll() {
        return repo.findAllSorted();
    }

    // ✅ METODĂ NOUĂ - CU SORTARE ȘI FILTRARE
    public List<Loan> getAll(String sortBy, String direction,
                             String filterMemberName,
                             String filterStatus,
                             String filterLoanDate) {

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
        boolean hasLoanDateFilter = filterLoanDate != null && !filterLoanDate.trim().isEmpty();

        // Convert status string to enum if needed
        Loan.LoanStatus status = null;
        if (hasStatusFilter) {
            try {
                status = Loan.LoanStatus.valueOf(filterStatus.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                hasStatusFilter = false;
            }
        }

        // Convert date string to LocalDate if needed
        LocalDate loanDate = null;
        if (hasLoanDateFilter) {
            try {
                loanDate = LocalDate.parse(filterLoanDate.trim());
            } catch (Exception e) {
                hasLoanDateFilter = false;
            }
        }

        // 3️⃣ APLICĂ FILTRELE CORESPUNZĂTOARE

        // TOATE 3 FILTRE
        if (hasMemberNameFilter && hasStatusFilter && hasLoanDateFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndStatusAndLoanDate(
                    filterMemberName.trim(), status, loanDate, sort);
        }

        // 2 FILTRE: Member + Status
        if (hasMemberNameFilter && hasStatusFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndStatus(
                    filterMemberName.trim(), status, sort);
        }

        // 2 FILTRE: Member + LoanDate
        if (hasMemberNameFilter && hasLoanDateFilter) {
            return repo.findByMember_NameContainingIgnoreCaseAndLoanDate(
                    filterMemberName.trim(), loanDate, sort);
        }

        // 2 FILTRE: Status + LoanDate
        if (hasStatusFilter && hasLoanDateFilter) {
            return repo.findByStatusAndLoanDate(status, loanDate, sort);
        }

        // 1 FILTRU: Doar Member Name
        if (hasMemberNameFilter) {
            return repo.findByMember_NameContainingIgnoreCase(filterMemberName.trim(), sort);
        }

        // 1 FILTRU: Doar Status
        if (hasStatusFilter) {
            return repo.findByStatus(status, sort);
        }

        // 1 FILTRU: Doar LoanDate
        if (hasLoanDateFilter) {
            return repo.findByLoanDate(loanDate, sort);
        }

        // FĂRĂ FILTRE: Doar sortare
        return repo.findAll(sort);
    }

    public Loan getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Loan save(Loan loan) {
        return repo.save(loan);
    }

    public void add(String id, Loan loan) {
        loan.setId(id);
        repo.save(loan);
    }

    public void update(String id, Loan loan) {
        loan.setId(id);
        repo.save(loan);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Loan::getId)
                .filter(id -> id != null && id.startsWith("Loan"))
                .map(id -> id.substring(4))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "Loan" + (maxNumber + 1);
    }

    public Loan newForForm() {
        Loan loan = new Loan();
        loan.setId(generateNextId());
        return loan;
    }
}