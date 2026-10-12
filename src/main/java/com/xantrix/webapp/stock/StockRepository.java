package com.xantrix.webapp.stock;

import com.xantrix.webapp.articoli.Articoli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select s from Stock s where s.articolo.codArt = :codArt")
    List<Stock> findStockByArticoloCodArt(@Param("codArt") String codArt);

}
