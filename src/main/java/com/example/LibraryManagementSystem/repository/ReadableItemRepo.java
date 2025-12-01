package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.ReadableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadableItemRepo extends JpaRepository<ReadableItem, String> {

    List<ReadableItem> findByPublicationId(String publicationId);
    List<ReadableItem> findByStatus(ReadableItem.ItemStatus status);
    ReadableItem findByBarcode(String barcode);

    // Sortare naturală: Item1, Item2, ..., Item10, Item11
    @Query("SELECT i FROM ReadableItem i ORDER BY CAST(SUBSTRING(i.id, 5) AS int)")
    List<ReadableItem> findAllSorted();
}