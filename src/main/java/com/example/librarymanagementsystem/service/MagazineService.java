package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.repository.PublicationRepo;

public class MagazineService extends BaseService<Publication> {

    public MagazineService(PublicationRepo repo) {
        super(repo);
    }
}
