package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.ReadableItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadableItemRepo extends JpaRepository<ReadableItem, String> {

    // ✅ SORTARE NATURALĂ - actualizată pentru formatul ITEM001
    @Query("SELECT i FROM ReadableItem i ORDER BY CAST(SUBSTRING(i.id, 5, 3) AS int)")
    List<ReadableItem> findAllSorted();

    // ✅ SORTARE DINAMICĂ
    List<ReadableItem> findAll(Sort sort);

    // ✅ FILTRARE - 1 filtru
    List<ReadableItem> findByPublicationIdContainingIgnoreCase(String publicationId, Sort sort);
    List<ReadableItem> findByBarcodeContainingIgnoreCase(String barcode, Sort sort);
    List<ReadableItem> findByStatus(ReadableItem.ItemStatus status, Sort sort);

    // ✅ FILTRARE - 2 filtre
    List<ReadableItem> findByPublicationIdContainingIgnoreCaseAndBarcodeContainingIgnoreCase(
            String publicationId, String barcode, Sort sort);
    List<ReadableItem> findByPublicationIdContainingIgnoreCaseAndStatus(
            String publicationId, ReadableItem.ItemStatus status, Sort sort);
    List<ReadableItem> findByBarcodeContainingIgnoreCaseAndStatus(
            String barcode, ReadableItem.ItemStatus status, Sort sort);

    // ✅ FILTRARE - toate 3 filtre
    List<ReadableItem> findByPublicationIdContainingIgnoreCaseAndBarcodeContainingIgnoreCaseAndStatus(
            String publicationId, String barcode, ReadableItem.ItemStatus status, Sort sort);

    // Metode existente
    List<ReadableItem> findByPublicationId(String publicationId);
    ReadableItem findByBarcode(String barcode);

}