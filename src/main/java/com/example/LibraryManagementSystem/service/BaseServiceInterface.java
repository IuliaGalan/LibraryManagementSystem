package com.example.librarymanagementsystem.service;

//import com.example.librarymanagementsystem.repository.InMemoryBaseRepo;

import java.util.List;

public interface BaseServiceInterface<T> {

    public void add(String id, T entity);

    T getById(String id);

    List<T> getAll();

    void update(String id, T entity);

    void delete(String id);
}

