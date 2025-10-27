package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.repository.MemberRepo;

public class MemberService extends BaseService<Member> {

    public MemberService(MemberRepo repo) {
        super(repo);
    }
}
