package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {

    // ✅ SORTARE NATURALĂ - compatibilă cu toate bazele de date
    @Query("SELECT m FROM Member m ORDER BY " +
            "CASE WHEN LENGTH(m.id) = LENGTH('MEM') + 1 THEN 0 " +
            "WHEN LENGTH(m.id) = LENGTH('MEM') + 2 THEN 1 " +
            "WHEN LENGTH(m.id) = LENGTH('MEM') + 3 THEN 2 " +
            "ELSE 3 END, m.id")
    List<Member> findAllSorted();

    List<Member> findAll(Sort sort);

    List<Member> findByNameContainingIgnoreCase(String name, Sort sort);
    List<Member> findByAddressContainingIgnoreCase(String address, Sort sort);
    List<Member> findByEmailContainingIgnoreCase(String email, Sort sort);

    List<Member> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
            String name, String address, Sort sort);
    List<Member> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String email, Sort sort);
    List<Member> findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String address, String email, Sort sort);

    List<Member> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String address, String email, Sort sort);

    Member findByEmail(String email);
}