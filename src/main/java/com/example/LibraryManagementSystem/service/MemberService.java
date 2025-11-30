package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.repository.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepo repo;

    public MemberService(MemberRepo repo) {
        this.repo = repo;
    }

    public List<Member> getAll() {
        return repo.findAll();
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
        return "M" + (repo.count() + 1);
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