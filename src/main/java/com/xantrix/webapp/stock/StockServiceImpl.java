package com.xantrix.webapp.stock;
import com.xantrix.webapp.articoli.Articoli;
import com.xantrix.webapp.articoli.ArticoliRepository;

import com.xantrix.webapp.stock.StockRepository;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;
    private final ArticoliRepository articoliRepository;

    private final Validator validator;

    public ResponseEntity<List<Stock>> findAll() throws NotFoundException {

        List<Stock> res = stockRepository.findAll();
        if(res.isEmpty()){
            throw new NotFoundException("Stock Not Found");
        }
        return ResponseEntity.ok().body(res);
    }

    @Override
    @Transactional
    public void aggiornaValoreStock(Articoli articolo){
        if (articolo == null || articolo.getUm() == null) {
            return;
        }
        List<Stock> stock = stockRepository.findStockByArticoloCodArt(articolo.getCodArt());
        if (stock.isEmpty()) {
            return;
        }
        for(Stock stock1 : stock){

            stock1.setValoreStock(articolo);
        }
        stockRepository.saveAll(stock);
    }

    @Override
    @Transactional
    public ResponseEntity<Stock> insertStock(@Valid StockDto stockDto) throws DuplicateException, NotFoundException {

        Stock stock = new Stock(stockDto);

        Articoli articolo = articoliRepository.findById(stockDto.getCodart())
                .orElseThrow(() -> new NotFoundException(
                        "L'articolo riferito nello stock non esiste"));

        stock.setArticolo(articolo);
        stock.setValoreStock(stock.getArticolo());
        Stock savedStock = stockRepository.save(stock);
        return ResponseEntity.ok().body(savedStock);
    }


    @Transactional
    public ResponseEntity<Stock> updateStock(@Valid Stock stock) {
        if(stockRepository.findById(stock.getId()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        return new ResponseEntity<>(stockRepository.save(stock), HttpStatus.OK);
    }


}
