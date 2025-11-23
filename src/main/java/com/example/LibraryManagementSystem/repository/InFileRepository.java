package com.example.librarymanagementsystem.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class InFileRepository<T> implements RepositoryInterface<T> {

    private final Map<String, T> data = new LinkedHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path filePath;
    private final TypeReference<List<T>> listTypeRef;

    public InFileRepository(String filePath, TypeReference<List<T>> listTypeRef) {
        this.filePath = Path.of(filePath);
        this.listTypeRef = listTypeRef;
        loadFromDisk();
    }

    // ⬇️ 1. LOAD + verificare ID invalid + verificare ID duplicat
    private synchronized void loadFromDisk() {
        try {
            File f = filePath.toFile();

            if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
            if (!f.exists()) Files.writeString(filePath, "[]");

            List<T> list = mapper.readValue(f, listTypeRef);

            data.clear();

            for (T item : list) {
                String id = extractId(item);

                // ID gol sau null → invalid
                if (id == null || id.isBlank()) {
                    throw new RuntimeException(
                            "ID invalid găsit în fișier: " + filePath +
                                    " pentru entitatea " + item.getClass().getSimpleName()
                    );
                }

                // ID duplicat → nu respectă cerința de unicitate
                if (data.containsKey(id)) {
                    throw new RuntimeException(
                            "ID duplicat în fișier: " + id +
                                    " pentru entitatea " + item.getClass().getSimpleName()
                    );
                }

                data.put(id, item);
            }

        } catch (Exception e) {
            throw new RuntimeException("Nu pot încărca din " + filePath + ": " + e.getMessage(), e);
        }
    }

    private synchronized void saveToDisk() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), new ArrayList<>(data.values()));
        } catch (Exception e) {
            throw new RuntimeException("Nu pot salva în " + filePath + ": " + e.getMessage(), e);
        }
    }

    private String extractId(T item) {
        try {
            return (String) item.getClass().getMethod("getId").invoke(item);
        } catch (Exception e) {
            throw new RuntimeException("Entitatea nu are getId(): " + item.getClass().getSimpleName(), e);
        }
    }

    @Override
    public synchronized List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public synchronized T findById(String id) {
        return data.get(id);
    }

    // ⬇️ 2. SAVE → doar creare, nu și update
    @Override
    public synchronized void save(String id, T entity) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID invalid");

        // asigură unicitatea la CREATE (cerință proiect)
        if (data.containsKey(id)) {
            throw new IllegalArgumentException("Există deja o entitate cu ID-ul: " + id);
        }

        data.put(id, entity);
        saveToDisk();
    }

    // ⬇️ 3. UPDATE separat — obligatoriu pentru CRUD complet clar
    public synchronized void update(String id, T entity) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID invalid");

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("Nu există entitate cu ID-ul: " + id);
        }

        data.put(id, entity);
        saveToDisk();
    }

    @Override
    public synchronized void delete(String id) {
        if (data.remove(id) != null) {
            saveToDisk();
        }
    }
}
