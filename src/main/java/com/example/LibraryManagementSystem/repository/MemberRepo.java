package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {

    // ✅ VALIDĂRI
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, String id);

    // ✅ SORTARE NATURALĂ
    @Query("SELECT m FROM Member m ORDER BY CAST(SUBSTRING(m.id, 2) AS int)")
    List<Member> findAllSorted();

    // ✅ SORTARE DINAMICĂ
    List<Member> findAll(Sort sort);

    // ✅ FILTRARE - 1 filtru
    List<Member> findByNameContainingIgnoreCase(String name, Sort sort);
    List<Member> findByAddressContainingIgnoreCase(String address, Sort sort);
    List<Member> findByEmailContainingIgnoreCase(String email, Sort sort);

    // ✅ FILTRARE - 2 filtre
    List<Member> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(
            String name, String address, Sort sort);
    List<Member> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String email, Sort sort);
    List<Member> findByAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String address, String email, Sort sort);

    // ✅ FILTRARE - toate 3 filtre
    List<Member> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name, String address, String email, Sort sort);
}