package com.example.LibraryManagementSystem.service;

import com.example.librarymanagementsystem.repository.InMemoryBaseRepo;

import java.util.List;

public interface BaseServiceInterface<T> {

    public void add(String id, T entity);

    public T getById(String id);

    public List<T> getAll();

    public void update(String id, T entity);

    public void delete(String id);
}

