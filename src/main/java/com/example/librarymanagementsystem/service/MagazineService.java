package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.repository.MagazineRepo;
import org.springframework.stereotype.Service;

@Service
public class MagazineService extends BaseService<MagazineDetails> {

    public MagazineService(MagazineRepo repo) {
        super(repo);
    }
}
