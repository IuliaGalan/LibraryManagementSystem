package com.example.librarymanagementsystem.repository;

import java.io.Serializable;
import java.util.List;

public interface RepositoryInterface<T> {
    List<T> findAll();
    T findById(String id);
    void save(String id, T entity);
    void delete(String id);
}
