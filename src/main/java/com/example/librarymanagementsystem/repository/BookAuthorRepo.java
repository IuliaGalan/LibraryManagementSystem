package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookAuthorRepo extends JpaRepository<BookAuthor, String> {

    // toate legăturile pentru o carte (după id-ul cărții)
    List<BookAuthor> findByBook_Id(String bookId);

    // toate legăturile pentru un autor (după id-ul autorului)
    List<BookAuthor> findByAuthor_Id(String authorId);

    // verifică dacă există deja o legătură pentru aceeași pereche carte–autor
    boolean existsByBook_IdAndAuthor_Id(String bookId, String authorId);

    // <<< sortare naturală după partea numerică din ID ("BA1", "BA2", ..., "BA10")
    @Query("SELECT ba FROM BookAuthor ba ORDER BY CAST(SUBSTRING(ba.id, 3) AS int)")
    List<BookAuthor> findAllSorted();
}
