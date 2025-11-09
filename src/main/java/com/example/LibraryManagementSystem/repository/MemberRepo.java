package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Member ;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepo extends InFileRepository<Member> {
    public MemberRepo() {
        super("src/main/resources/data/reservation.json",
                new TypeReference<java.util.List<Member>>() {
                });
    }
}