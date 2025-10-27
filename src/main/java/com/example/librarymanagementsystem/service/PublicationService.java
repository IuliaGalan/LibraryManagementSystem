package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.repository.PublicationRepo;

public class PublicationService extends BaseService<Publication> {

    public PublicationService(PublicationRepo repo) {
        super(repo);
    }
}
