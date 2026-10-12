package com.xantrix.webapp.stock;

import com.xantrix.webapp.articoli.Articoli;
import com.xantrix.webapp.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StockService {

    ResponseEntity<List<Stock>> findAll() throws NotFoundException;

    ResponseEntity<Stock> insertStock(StockDto stockdto) throws NotFoundException;

    void aggiornaValoreStock(Articoli articolo);

    ResponseEntity<Stock> updateStock(@Valid() Stock stock);
}
