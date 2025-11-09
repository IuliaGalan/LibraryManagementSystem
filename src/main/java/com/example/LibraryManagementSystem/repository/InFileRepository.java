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

    private synchronized void loadFromDisk() {
        try {
            File f = filePath.toFile();
            if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
            if (!f.exists()) Files.writeString(filePath, "[]");
            List<T> list = mapper.readValue(f, listTypeRef);
            data.clear();
            for (T item : list) {
                data.put(extractId(item), item);
            }
        } catch (Exception e) {
            throw new RuntimeException("Nu pot încărca din " + filePath + ": " + e.getMessage(), e);
        }
    }

    private synchronized void saveToDisk() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), new ArrayList<>(data.values()));
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

    @Override public synchronized List<T> findAll() { return new ArrayList<>(data.values()); }
    @Override public synchronized T findById(String id) { return data.get(id); }

    @Override
    public synchronized void save(String id, T entity) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID invalid");
        data.put(id, entity); // create + update
        saveToDisk();
    }

    @Override
    public synchronized void delete(String id) {
        if (data.remove(id) != null) saveToDisk();
    }
}
