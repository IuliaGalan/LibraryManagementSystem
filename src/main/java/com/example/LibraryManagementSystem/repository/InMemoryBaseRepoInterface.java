package com.example.librarymanagementsystem.repository;

import java.util.List;

public interface InMemoryBaseRepoInterface<T> {

    public List<T> findAll();

    public T findById(String id);

    public void save(String id, T entity);

    public void delete(String id);
}
