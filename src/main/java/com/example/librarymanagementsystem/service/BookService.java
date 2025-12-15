package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.BookDetails;
import com.example.librarymanagementsystem.repository.BookRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepo repo;

    public BookService(BookRepo repo) {
        this.repo = repo;
    }

    public List<BookDetails> getAll() {
        return repo.findAllSorted();
    }

    //SORTARE ȘI FILTRARE
    public List<BookDetails> getAll(String sortBy, String direction,
                                    String filterTitle, String filterGenre) {

        //Construiește sortarea
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        //Verifică filtrele active
        boolean hasTitleFilter = filterTitle != null && !filterTitle.trim().isEmpty();
        boolean hasGenreFilter = filterGenre != null && !filterGenre.trim().isEmpty();

        //Aplică filtrele corespunzătoare
        if (hasTitleFilter && hasGenreFilter) {
            return repo.findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(
                    filterTitle.trim(), filterGenre.trim(), sort);
        } else if (hasTitleFilter) {
            return repo.findByTitleContainingIgnoreCase(filterTitle.trim(), sort);
        } else if (hasGenreFilter) {
            return repo.findByGenreContainingIgnoreCase(filterGenre.trim(), sort);
        } else {
            return repo.findAll(sort);
        }
    }

    public BookDetails getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public BookDetails save(BookDetails book) {
        return repo.save(book);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        return "B" + (repo.count() + 1);
    }

    public BookDetails newForForm() {
        BookDetails book = new BookDetails();
        book.setId(generateNextId());
        return book;
    }

    public boolean existsByTitle(String title) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCase(title.trim());
    }

    public boolean existsByTitleForOtherBook(String title, String excludedId) {
        if (title == null) return false;
        return repo.existsByTitleIgnoreCaseAndIdNot(title.trim(), excludedId);
    }
}