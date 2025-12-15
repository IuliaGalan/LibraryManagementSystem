package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.ReadableItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadableItemRepo extends JpaRepository<ReadableItem, String> {

    // ✅ SORTARE NATURALĂ CORECTĂ pentru ITEM001, ITEM002, ... ITEM010, ITEM011
    @Query("SELECT i FROM ReadableItem i ORDER BY LENGTH(i.id), i.id")
    List<ReadableItem> findAllSorted();

    List<ReadableItem> findAll(Sort sort);

    List<ReadableItem> findByPublicationIdContainingIgnoreCase(String publicationId, Sort sort);
    List<ReadableItem> findByBarcodeContainingIgnoreCase(String barcode, Sort sort);
    List<ReadableItem> findByStatus(ReadableItem.ItemStatus status, Sort sort);

    List<ReadableItem> findByPublicationIdContainingIgnoreCaseAndBarcodeContainingIgnoreCase(
            String publicationId, String barcode, Sort sort);
    List<ReadableItem> findByPublicationIdContainingIgnoreCaseAndStatus(
            String publicationId, ReadableItem.ItemStatus status, Sort sort);
    List<ReadableItem> findByBarcodeContainingIgnoreCaseAndStatus(
            String barcode, ReadableItem.ItemStatus status, Sort sort);

    List<ReadableItem> findByPublicationIdContainingIgnoreCaseAndBarcodeContainingIgnoreCaseAndStatus(
            String publicationId, String barcode, ReadableItem.ItemStatus status, Sort sort);

    List<ReadableItem> findByPublicationId(String publicationId);
    ReadableItem findByBarcode(String barcode);
}