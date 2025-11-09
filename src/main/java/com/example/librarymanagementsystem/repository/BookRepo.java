package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookDetails;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepo extends InMemoryBaseRepo<BookDetails> {}