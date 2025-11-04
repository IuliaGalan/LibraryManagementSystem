package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.repository.InMemoryBaseRepo;
import java.util.List;
//asta nu trb sa fie influentata indiferent de cu ne procesam datele. daca scot layerul de repo si pun layerul de baze de date nu ar trebui sa crape
public abstract class BaseService<T> {

    protected final InMemoryBaseRepo<T> repo;

    public BaseService(InMemoryBaseRepo<T> repo) {
        this.repo = repo;
    }

    public void add(String id, T entity) {
        if (repo.findById(id) == null) {
            repo.save(id, entity);
        }
    }

    public T getById(String id) {
        return repo.findById(id);
    }

    public List<T> getAll() {
        return repo.findAll();
    }

    public void update(String id, T entity) {
        if (repo.findById(id) != null) {
            repo.save(id, entity);
        }
    }

    public void delete(String id) {
        if (repo.findById(id) != null) {
            repo.delete(id);
        }
    }
}
