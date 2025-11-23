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
        // CREATE – repo (InFileRepository) verifică ID invalid / duplicat
        repo.save(id, entity);
    }

    @Override
    public T getById(String id) {
        // READ
        return repo.findById(id);
    }

    @Override
    public List<T> getAll() {
        // READ
        return repo.findAll();
    }

    @Override
    public void update(String id, T entity) {
        // UPDATE – repo.update(...) verifică dacă entitatea există
        repo.update(id, entity);
    }

    @Override
    public void delete(String id) {
        // DELETE
        repo.delete(id);
    }
}
