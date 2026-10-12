package com.xantrix.webapp.stock;

import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/stock")
public class StockController {

    private final StockService stockService;

    @RequestMapping(value = "/")
    public ResponseEntity<List<Stock>> getStock() throws NotFoundException {
        return stockService.findAll();
    }
    @PostMapping(value = "/insert")
    public ResponseEntity<Stock> InsertStock(@RequestBody StockDto stock)
            throws DuplicateException, NotFoundException {
        return stockService.insertStock(stock);

    }
    @PutMapping(value = "/update")
    public ResponseEntity<Stock> UpdateStock(@RequestBody @Valid() Stock stock) throws NotFoundException {
        return stockService.updateStock(stock);
    }

}
