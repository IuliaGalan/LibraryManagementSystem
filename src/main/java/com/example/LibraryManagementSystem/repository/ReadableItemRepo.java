package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.ReadableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadableItemRepo extends JpaRepository<ReadableItem, String> {

    // Găsește toate itemele dintr-o bibliotecă
    List<ReadableItem> findByLibrary_Id(String libraryId);


    List<ReadableItem> findByPublicationId(String publicationId);

    // ✅ CORECT - caută după status
    List<ReadableItem> findByStatus(ReadableItem.ItemStatus status);

    // ✅ CORECT - caută după barcode
    ReadableItem findByBarcode(String barcode);
    // Verifică dacă există un item cu acest barcode
    boolean existsByBarcode(String barcode);

    // Verifică dacă există alt item cu acest barcode (pentru update)
    boolean existsByBarcodeAndIdNot(String barcode, String id);
}