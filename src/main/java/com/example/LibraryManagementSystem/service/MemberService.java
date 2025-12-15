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

    public List<Member> getAll() {
        return repo.findAllSorted();
    }

    public List<Member> getAll(String sortBy, String direction,
                               String filterName,
                               String filterAddress,
                               String filterEmail) {
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        boolean hasNameFilter = filterName != null && !filterName.trim().isEmpty();
        boolean hasAddressFilter = filterAddress != null && !filterAddress.trim().isEmpty();
        boolean hasEmailFilter = filterEmail != null && !filterEmail.trim().isEmpty();

        if (hasNameFilter && hasAddressFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), filterEmail.trim(), sort);
        }

        if (hasNameFilter && hasAddressFilter) {
            return repo.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
                    filterName.trim(), filterAddress.trim(), sort);
        }

        if (hasNameFilter && hasEmailFilter) {
            return repo.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterName.trim(), filterEmail.trim(), sort);
        }

        if (hasAddressFilter && hasEmailFilter) {
            return repo.findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
                    filterAddress.trim(), filterEmail.trim(), sort);
        }

        if (hasNameFilter) {
            return repo.findByNameContainingIgnoreCase(filterName.trim(), sort);
        }

        if (hasAddressFilter) {
            return repo.findByAddressContainingIgnoreCase(filterAddress.trim(), sort);
        }

        if (hasEmailFilter) {
            return repo.findByEmailContainingIgnoreCase(filterEmail.trim(), sort);
        }

        return repo.findAll(sort);
    }

    public Member getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Member save(Member member) {
        return repo.save(member);
    }

    public void add(String id, Member member) {
        member.setId(id);
        repo.save(member);
    }

    public void update(String id, Member member) {
        member.setId(id);
        repo.save(member);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        int maxNumber = repo.findAll().stream()
                .map(Member::getId)
                .filter(id -> id != null && id.toUpperCase().startsWith("MEM"))
                .map(id -> {
                    String numericPart = id.substring(3);
                    try {
                        return Integer.parseInt(numericPart);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0);

        return String.format("MEM%03d", maxNumber + 1);
    }

    public Member newForForm() {
        Member member = new Member();
        member.setId(generateNextId());
        return member;
    }

    // ✅ VALIDĂRI BUSINESS
    public boolean existsById(String id) {
        if (id == null) return false;
        return repo.existsById(id);
    }

    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return repo.findByEmail(email.trim()) != null;
    }

    public boolean existsByEmailForOtherMember(String email, String excludedId) {
        if (email == null) return false;
        Member existing = repo.findByEmail(email.trim());
        return existing != null && !existing.getId().equals(excludedId);
    }
}