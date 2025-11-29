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

    // READ - toți membrii
    public List<Member> getAll() {
        return repo.findAll();
    }

    // READ - un membru după ID
    public Member getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // CREATE & UPDATE
    public Member save(Member member) {
        return repo.save(member);
    }

    // DELETE
    public void delete(String id) {
        repo.deleteById(id);
    }

    // Generare ID automat
    public String generateNextId() {
        return "M" + (repo.count() + 1);
    }

    // Helper pentru formular nou
    public Member newForForm() {
        Member member = new Member();
        member.setId(generateNextId());
        return member;
    }

    // --- Validări business ---

    // Verifică dacă există un membru cu acest email (pentru CREATE)
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return repo.existsByEmailIgnoreCase(email.trim());
    }

    // Verifică dacă există alt membru cu acest email (pentru UPDATE)
    public boolean existsByEmailForOtherMember(String email, String excludedId) {
        if (email == null) return false;
        return repo.existsByEmailIgnoreCaseAndIdNot(email.trim(), excludedId);
    }
}