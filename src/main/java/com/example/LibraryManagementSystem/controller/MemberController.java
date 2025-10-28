package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    // test simplu
    @GetMapping("/hello")
    public String sayHello() {
        return "MemberController works!";
    }

    // returnează toți membrii
    @GetMapping
    public List<Member> getAllMembers() {
        return service.getAll();
    }

    // adaugă un membru nou
    @PostMapping
    public void addMember(@RequestBody Member member) {
        service.add(member.getId(), member);
    }

    // caută un membru după id
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable String id) {
        return service.getById(id);
    }

    // șterge un membru
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable String id) {
        service.delete(id);
    }
}
