package com.example.librarymanagementsystem.repository;
import java.util.*;
public class InMemoryBaseRepo<T> {

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
// o clasa doar cu interfata,care ne spune contractul repo adica ce trb un repo sa faca adica add delete
//pt ca avem asta trb sa vedem cum implementam,daca vreau sa adaug cititrea in fisier,automat se va
// modifica. ne facem alta clasa baserepo in memory repo care implem aceea clasacare e interfata si
// doar returneaz/implemen codul de la prima clasa.
//trb sa fie de tip interfata. modalitat eprin care sa restrangem toate repo sa faca asta. facem crud  repo
//toate repo trb sa aiba signatura care sunt in interfata
//pornim cu interfata,apoi avem fiecare clasa care extinde in memory.
