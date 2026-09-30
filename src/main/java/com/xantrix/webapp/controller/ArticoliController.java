package com.xantrix.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Barcode;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.service.ArticoliService;
import com.xantrix.webapp.service.BarcodeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.xantrix.webapp.exception.NotFoundException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/articoli")
public class ArticoliController {

    private static Logger logger = LoggerFactory.getLogger(ArticoliController.class);
    @Autowired
    private ArticoliService articoliService;
    @Autowired
    private BarcodeService barcodeService;

    @Autowired
    private ResourceBundleMessageSource errMessage;

    @RequestMapping(value = "cerca/ean/{barcode}", method = RequestMethod.GET, produces = "application/json" )
    public ResponseEntity<Articoli> ListArtByEan(@PathVariable("barcode") String barcode) throws NotFoundException {

        Articoli articolo;
        Barcode ean = barcodeService.SelByBarcode(barcode);
        if (ean == null){
            String ErrMsg = "Il barcode " + barcode + " non è stato trovato!";

            logger.warn(ErrMsg);

            throw new NotFoundException(ErrMsg);
        }
        articolo = ean.getArticolo();
        return new ResponseEntity<Articoli>(articolo,HttpStatus.OK);

    }
    @RequestMapping(value = "/cerca/codice/{codart}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Articoli> ListArtByCod(@PathVariable("codart") String codart) throws NotFoundException{

        logger.info("******cerco l'articolo dal codart: " + codart);
        Articoli articolo =  articoliService.SelByCodArt(codart);
        if (articolo == null){

            String message = "L'articolo con codice " + codart + " non è stato trovato!";
            logger.warn(message);
            throw new NotFoundException(message);

        }

        return new ResponseEntity<Articoli>(articolo,HttpStatus.OK);
    }

    @RequestMapping(value = "cerca/descrizione/{descrizione}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Articoli>>  ListArtByDesc(@PathVariable("descrizione") String descrizione) throws NotFoundException{
        logger.info("****** cerco  l'articolo dal descrizione: " + descrizione);
        List<Articoli> articoli =  articoliService.SelByDescrizione(descrizione + "%");
        if (articoli.isEmpty()){
            String message = "non sono stati trovati articoli con descrizione: " + descrizione ;
            logger.warn(message);
            throw new NotFoundException(message);
        }
        return new ResponseEntity<List<Articoli>>(articoli,HttpStatus.OK);

    }

    @RequestMapping(value = "cerca/descrizione/{descrizione}/{page}/{rows}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Articoli>>  ListArtByDesc(@PathVariable("descrizione") String descrizione, @PathVariable("page") int pageNum,@PathVariable("rows") int RecXPage)  throws NotFoundException{
        logger.info("****** cerco  l'articolo dal descrizione: " + descrizione);

        RecXPage = (RecXPage < 0) ? 10 : RecXPage;

        List<Articoli> articoli =  articoliService.SelByDescrizione(descrizione + "%", PageRequest.of(pageNum, RecXPage));

        if (articoli == null){
            String message = new String("non sono stati trovati articoli con descrizione: " + descrizione );
            logger.warn(message);
            throw new NotFoundException(message);
        }
        return new ResponseEntity<List<Articoli>>(articoli,HttpStatus.OK);

    }
    @RequestMapping(value = "/inserisci", method = RequestMethod.POST)
    public ResponseEntity<Articoli> createArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult,
                                              UriComponentsBuilder ucBuilder)
            throws BindingException, DuplicateException{
        if (bindingResult.hasErrors()){
            String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
            logger.warn(MsgErr);
            throw new BindingException(MsgErr);
        }
        logger.debug("controllo se l'articolo"+ articolo.getCodArt() + " è già presente ");
        if (articoliService.SelByCodArt(articolo.getCodArt()) != null){

            String message = "Articolo "+ articolo.getCodArt() +" presente in anagrafica! Impossibile utilizzare il metodo POST";
            logger.warn(message);
            throw new DuplicateException(message);

        }

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        articoliService.InsArticolo(articolo);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Inserimento Articolo " + articolo.getCodArt() + " Eseguita Con Successo");

        return new ResponseEntity<Articoli>(headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/modifica", method =  RequestMethod.PUT)
    public ResponseEntity<Articoli> UpdArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult) throws NotFoundException,BindingException{
        if (bindingResult.hasErrors()){
            String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
            logger.warn(MsgErr);
            throw new BindingException(MsgErr);
        }
        logger.info("controllo se l'articolo "+ articolo.getCodArt() + " è presente");
        if( articoliService.SelByCodArt( articolo.getCodArt()) ==null){

            String message = "l'articolo " + articolo.getCodArt() + "non è presente in anagrafica! Impossibile utilizzare il metodo PUT";
            logger.warn(message);
            throw new NotFoundException(message);

        }

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        articoliService.InsArticolo(articolo);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message","Modifica Articolo" + articolo.getCodArt() + "Eseguita Con Successo");

        return new ResponseEntity<Articoli> (headers,HttpStatus.CREATED);


    }

    @RequestMapping(value = "/elimina/{codart}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt)
            throws  NotFoundException
    {
        logger.info("Eliminiamo l'articolo con codice " + CodArt);

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        Articoli articolo = articoliService.SelByCodArt(CodArt);

        if (articolo == null)
        {
            String MsgErr = String.format("Articolo %s non presente in anagrafica! ",CodArt);

            logger.warn(MsgErr);

            throw new NotFoundException(MsgErr);
        }

        articoliService.DelArticolo(articolo);

        responseNode.put("code", HttpStatus.OK.value());
        responseNode.put("message", "Eliminazione Articolo " + CodArt + " Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }

}
