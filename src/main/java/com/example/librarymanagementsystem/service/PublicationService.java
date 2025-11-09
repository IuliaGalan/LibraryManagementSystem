package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Publication;
import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.model.MagazineDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicationService {

    private final BookService bookService;
    private final MagazineService magazineService;

    public PublicationService(BookService bookService, MagazineService magazineService) {
        this.bookService = bookService;
        this.magazineService = magazineService;
    }

    public List<Publication> getAll() {
        List<Publication> list = new ArrayList<>();
        List<BookDetails> books = bookService.getAll();
        List<MagazineDetails> mags = magazineService.getAll();
        if (books != null) list.addAll(books);
        if (mags != null) list.addAll(mags);
        return list; // conține BookDetails + MagazineDetails (ambele extind Publication)
    }

    public Publication getById(String id) {
        Publication p = bookService.getById(id);
        if (p != null) return p;
        return magazineService.getById(id);
    }
}
