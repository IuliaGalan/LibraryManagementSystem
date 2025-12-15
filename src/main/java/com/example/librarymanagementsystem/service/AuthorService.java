package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.AuthorRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepo repo;

    public AuthorService(AuthorRepo repo) {
        this.repo = repo;
    }

    //returneaza toti autorii din repo
    public List<Author> getAll() {
        return repo.findAllSorted();
    }

    //SORTARE ȘI FILTRARE
    public List<Author> getAll(String sortBy, String direction,
                               String filterName, String filterNationality) {

        //Construiește sortarea
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        //Verifică daca filtrele sunt active
        boolean hasNameFilter = filterName != null && !filterName.trim().isEmpty();
        boolean hasNationalityFilter = filterNationality != null && !filterNationality.trim().isEmpty();

        //Aplică filtrele corespunzătoare
        if (hasNameFilter && hasNationalityFilter) {
            return repo.findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCase(
                    filterName.trim(), filterNationality.trim(), sort);
        } else if (hasNameFilter) {
            return repo.findByNameContainingIgnoreCase(filterName.trim(), sort);
        } else if (hasNationalityFilter) {
            return repo.findByNationalityContainingIgnoreCase(filterNationality.trim(), sort);
        } else {
            return repo.findAll(sort);
        }
    }


    public Author getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Author save(Author a) {
        return repo.save(a);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String generateNextId() {
        return "A" + (repo.count() + 1);
    }

    public Author newForForm() {
        Author a = new Author();
        a.setId(generateNextId());
        return a;
    }

    //Create
    public boolean existsByName(String name) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCase(name.trim());
    }

    //Update
    public boolean existsByNameForOtherAuthor(String name, String excludedId) {
        if (name == null) return false;
        return repo.existsByNameIgnoreCaseAndIdNot(name.trim(), excludedId);
    }
}