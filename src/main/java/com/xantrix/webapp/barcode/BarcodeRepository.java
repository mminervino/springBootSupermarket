package com.xantrix.webapp.barcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BarcodeRepository extends JpaRepository<Barcode, String> {
    Barcode findByBarcode(String barcode);

    @Query(value = "SELECT b.BARCODE FROM barcode b " +
            "JOIN articoli a ON a.CODART = b.CODART " +
            "WHERE a.IDFAMASS = :famAssortID",
            nativeQuery = true)
    List<String> findBarcodeByFamAssort(@Param("famAssortID") String famAssortID);
}
