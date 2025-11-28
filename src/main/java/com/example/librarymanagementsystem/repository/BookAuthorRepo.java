package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepo extends JpaRepository<BookAuthor, String> {
}
