package com.example.librarymanagementsystem.repository;
import java.util.*;
public class BaseRepo<T> {

    protected Map<String, T> data = new LinkedHashMap<>();

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public T findById(String id) {
        return data.get(id);
    }

    public void save(String id, T entity) {
        data.put(id, entity);
    }

    public void delete(String id) {
        data.remove(id);
    }
}
