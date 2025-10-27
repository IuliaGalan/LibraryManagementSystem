package com.example.librarymanagementsystem.model;

import java.util.Objects;

/**
 *
 * Relații (prin ID-uri):
 *  - Library 1 → N Reservation
 *  - Member  1 → N Reservation
 *  - ReadableItem 1 → N Reservation
 */
public class Reservation {

    private String id;
    private String memberId;
    private String readableItemId;
    private String date;
    private String status;

    public Reservation(String id, String memberId, String readableItemId, String date, String status) {
        this.id = id;
        this.memberId = memberId;
        this.readableItemId = readableItemId;
        this.date = date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReadableItemId() {
        return readableItemId;
    }

    public void setReadableItemId(String readableItemId) {
        this.readableItemId = readableItemId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}