package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.MagazineDetails;
import com.example.librarymanagementsystem.repository.MagazineRepo;

public class MagazineService extends BaseService<MagazineDetails> {

    public MagazineService(MagazineRepo repo) {
        super(repo);
    }
}
