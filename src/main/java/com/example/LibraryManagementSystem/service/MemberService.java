package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.repository.MemberRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepo repo;

    public MemberService(MemberRepo repo) {
        this.repo = repo;
    }

    // ✅ METODĂ VECHE (compatibilitate)
    public List<Member> getAll() {
        return repo.findAllSorted();
    }

    // ✅ METODĂ NOUĂ - CU SORTARE ȘI FILTRARE
    public List<Member> getAll(String sortBy, String direction,
                               String filterName,
                               String filterAddress,
                               String filterEmail) {

        // 1️⃣ CONSTRUIEȘTE SORTAREA
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // 2️⃣ VERIFICĂ CARE FILTRE SUNT ACTIVE
        boolean hasNameFilter = filterName != null && !filterName.trim().isEmpty();
        boolean hasAddressFilter = filterAddress != null && !filterAddress.trim().isEmpty();
        boolean hasEmailFilter = filterEmail != null && !filterEmail.trim().isEmpty();

        // 3️⃣ APLICĂ FILTRELE CORESPUNZĂTOARE

        // TOATE 3 FILTRE
        if (hasNameFilter && hasAddressFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), filterEmail.trim(), sort);
        }

        // 2 FILTRE: Nume + Adresă
        if (hasNameFilter && hasAddressFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), sort);
        }

        // 2 FILTRE: Nume + Email
        if (hasNameFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterEmail.trim(), sort);
        }

        // 2 FILTRE: Adresă + Email
        if (hasAddressFilter && hasEmailFilter) {
            return repo.findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterAddress.trim(), filterEmail.trim(), sort);
        }

        // 1 FILTRU: Doar Nume
        if (hasNameFilter) {
            return repo.findByNameContainingIgnoreCase(filterName.trim(), sort);
        }

        // 1 FILTRU: Doar Adresă
        if (hasAddressFilter) {
            return repo.findByAddressContainingIgnoreCase(filterAddress.trim(), sort);
        }

        // 1 FILTRU: Doar Email
        if (hasEmailFilter) {
            return repo.findByEmailContainingIgnoreCase(filterEmail.trim(), sort);
        }

        // FĂRĂ FILTRE: Doar sortare
        return repo.findAll(sort);
    }

    public Member getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Member save(Member member) {
        return repo.save(member);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Member::getId)
                .filter(id -> id != null && id.startsWith("M"))
                .map(id -> id.substring(1))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "M" + (maxNumber + 1);
    }

    public Member newForForm() {
        Member m = new Member();
        m.setId(generateNextId());
        return m;
    }

    // Business validation helpers
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return repo.existsByEmailIgnoreCase(email.trim());
    }

    public boolean existsByEmailForOtherMember(String email, String excludedId) {
        if (email == null) return false;
        return repo.existsByEmailIgnoreCaseAndIdNot(email.trim(), excludedId);
    }
}