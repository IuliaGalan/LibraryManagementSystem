package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.repository.PublicationRepo;
import org.springframework.stereotype.Service;

@Service
public class PublicationService extends BaseService<Publication> {

    public PublicationService(PublicationRepo repo) {
        super(repo);
    }
}
