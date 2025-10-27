package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.repository.BaseRepo;
import java.util.List;

public abstract class BaseService<T> {

    protected final BaseRepo<T> repo;

    public BaseService(BaseRepo<T> repo) {
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
