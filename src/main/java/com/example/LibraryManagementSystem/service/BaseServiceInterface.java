package com.example.librarymanagementsystem.service;

import java.util.List;

public interface BaseServiceInterface<T> {

    /**
     * Creează o nouă entitate cu ID-ul dat.
     * (CREATE)
     */
    void add(String id, T entity);

    /**
     * Găsește o entitate după ID.
     * (READ)
     */
    T getById(String id);

    /**
     * Returnează toate entitățile.
     * (READ)
     */
    List<T> getAll();

    /**
     * Actualizează o entitate existentă cu ID-ul dat.
     * (UPDATE)
     */
    void update(String id, T entity);

    /**
     * Șterge o entitate după ID.
     * (DELETE)
     */
    void delete(String id);
}
