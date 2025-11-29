package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.ReadableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadableItemRepo extends JpaRepository<ReadableItem, String> {

    // Găsește toate itemele dintr-o bibliotecă
    List<ReadableItem> findByLibrary_Id(String libraryId);

    // Găsește toate itemele unei publicații
    List<ReadableItem> findByPublication_Id(String publicationId);

    // Găsește itemele după status
    List<ReadableItem> findByStatus(ReadableItem.Status status);

    // Găsește un item după barcode
    ReadableItem findByBarcode(String barcode);

    // Verifică dacă există un item cu acest barcode
    boolean existsByBarcode(String barcode);

    // Verifică dacă există alt item cu acest barcode (pentru update)
    boolean existsByBarcodeAndIdNot(String barcode, String id);
}