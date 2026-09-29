package com.xantrix.webapp.controller;

import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Barcode;
import com.xantrix.webapp.service.ArticoliService;
import com.xantrix.webapp.service.BarcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/articoli")
public class ArticoliController {

    private static Logger logger = LoggerFactory.getLogger(ArticoliController.class);
    @Autowired
    private ArticoliService articoliService;
    @Autowired
    private BarcodeService barcodeService;

    @RequestMapping(value = "cerca/ean/{barcode}", method = RequestMethod.GET, produces = "application/json" )
    public ResponseEntity<Articoli> ListArtByEan(@PathVariable("barcode") String barcode){

        Articoli articolo;
        Barcode ean = barcodeService.SelByBarcode(barcode);
        if (ean == null){
            //gestisci ma ora non ho capo
        }
        articolo = ean.getArticoli();
        return new ResponseEntity<Articoli>(articolo,HttpStatus.OK);

    }

}
