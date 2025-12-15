package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BookAuthor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookAuthorRepo extends JpaRepository<BookAuthor, String> {

    List<BookAuthor> findByBook_Id(String bookId);
    List<BookAuthor> findByAuthor_Id(String authorId);
    boolean existsByBook_IdAndAuthor_Id(String bookId, String authorId);

    @Query("SELECT ba FROM BookAuthor ba ORDER BY CAST(SUBSTRING(ba.id, 3) AS int)")
    List<BookAuthor> findAllSorted();

    //SORTARE DINAMICĂ
    List<BookAuthor> findAll(Sort sort);

    //FILTRARE- Căutare în Book și Author

    // Filtrare după titlul cărții
    List<BookAuthor> findByBook_TitleContainingIgnoreCase(String bookTitle, Sort sort);

    // Filtrare după numele autorului
    List<BookAuthor> findByAuthor_NameContainingIgnoreCase(String authorName, Sort sort);

    // Filtrare după AMBELE
    List<BookAuthor> findByBook_TitleContainingIgnoreCaseAndAuthor_NameContainingIgnoreCase(
            String bookTitle, String authorName, Sort sort);
}