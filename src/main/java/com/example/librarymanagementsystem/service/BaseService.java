package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.repository.RepositoryInterface;
import java.util.List;

public abstract class BaseService<T> implements BaseServiceInterface<T> {

    protected final RepositoryInterface<T> repo;

    public BaseService(RepositoryInterface<T> repo) {
        this.repo = repo;
    }

    @Override
    public void add(String id, T entity) {
        if (repo.findById(id) == null) {
            repo.save(id, entity);
        }
    }

    @Override
    public T getById(String id) {
        return repo.findById(id);
    }

    @Override
    public List<T> getAll() {
        return repo.findAll();
    }

    @Override
    public void update(String id, T entity) {
        if (repo.findById(id) != null) {
            repo.save(id, entity);
        }
    }

    @Override
    public void delete(String id) {
        if (repo.findById(id) != null) {
            repo.delete(id);
        }
    }
}
