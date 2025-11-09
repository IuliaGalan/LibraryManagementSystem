package com.example.librarymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Publication {          // ← clasa e abstractă
    private String id;                       // ← fără final, ca să poată fi setat la deserializare
    private String title;
    private List<String> copyIds;

    public Publication() {                   // ← no-arg necesar pentru Jackson
        this.id = "";
        this.title = "";
        this.copyIds = new ArrayList<>();
    }

    public Publication(String id, String title) {   // ← constructorul NU e abstract (nu se poate)
        this.id = id;
        this.title = title;
        this.copyIds = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }          // setter necesar pentru JSON

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }  // return type void

    public List<String> getCopyIds() { return copyIds; }
    public void setCopyIds(List<String> copyIds) {
        this.copyIds = (copyIds != null) ? copyIds : new ArrayList<>();
    }

    // poți păstra în continuare metodele utilitare:
    public boolean addCopy(String newCopyId) {
        if (newCopyId == null || newCopyId.isEmpty()) return false;
        if (copyIds.contains(newCopyId)) return false;
        return copyIds.add(newCopyId);
    }

    public boolean removeCopy(String oldCopyId) { return copyIds.remove(oldCopyId); }
    public int numberOfCopies() { return copyIds.size(); }
}
