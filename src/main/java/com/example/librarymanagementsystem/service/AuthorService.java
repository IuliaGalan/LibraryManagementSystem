package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.RepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorService extends BaseService<Author> {

    public AuthorService(RepositoryInterface<Author> repo) {
        super(repo);
    }

    // generează ID-uri: A1, A2, A3 ...
    public String generateNextId() {
        int next = repo.findAll().stream()
                .map(Author::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.startsWith("A"))
                .map(id -> id.substring(1))
                .filter(num -> num.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;

        return "A" + next;
    }

    public Author newForForm() {
        Author a = new Author();
        a.setId(generateNextId());
        return a;
    }
}
