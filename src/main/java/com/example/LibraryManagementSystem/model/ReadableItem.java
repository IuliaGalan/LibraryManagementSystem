package com.example.librarymanagementsystem.model;



/**
 * ReadableItem reprezintă un exemplar fizic (carte, revistă) din bibliotecă.
 *
 * Relații (prin ID-uri):
 *  - Library 1 → N ReadableItem
 *  - ReadableItem → Publication (prin publicationId)
 */
public class ReadableItem {

    private String id;
    private String publicationId;
    private String barcode;
    private String status;
    // Constructor
    public ReadableItem(String id, String publicationId, String barcode, String status) {
        this.id = id;
        this.publicationId = publicationId;
        this.barcode = barcode;
        this.status = status;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}