package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Publication {

    private final String id;
    private String title;
    private List<String> copyIds;

    public Publication(String id, String title) {
        this.id = id;
        this.title = title;
        this.copyIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getCopyIds() {
        return copyIds;
    }

    public String setTitle(String title) {
        return this.title = title;
    }

    public boolean addCopy(String newCopyId) {
        if (newCopyId == null || newCopyId.isEmpty()) {
            return false;
        }
        if (copyIds.contains(newCopyId)) {
            return false;
        }
        return copyIds.add(newCopyId);
    }

    public boolean removeCopy(String oldCopyId) {
        return copyIds.remove(oldCopyId);
    }

    public int numberOfCopies() {
        return copyIds.size();
    }
}
