package com.xantrix.webapp.articoli;

import com.xantrix.webapp.dto.Dto;
import com.xantrix.webapp.famAssort.FamAssort;
import com.xantrix.webapp.famAssort.FamAssortService;
import io.swagger.v3.oas.annotations.Operation;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.barcode.BarcodeService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.xantrix.webapp.exception.NotFoundException;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articoli")
@RequiredArgsConstructor
public class ArticoliController {

    private final ArticoliService articoliService;
    private final BarcodeService barcodeService;
    private final FamAssortService  famAssortService;

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Articoli>> findAll(){
        return new ResponseEntity<>(articoliService.findAll(), HttpStatus.OK);
    }
    @GetMapping(value = "/conta",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dto>> countArticoliByFamAssort() throws NotFoundException {
        return new ResponseEntity(famAssortService.countArticoliByFamAssort(),HttpStatus.OK);
    }


    @GetMapping(value = "/{famAssort}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Articoli>> findByFamAssort(@PathVariable int famAssort) throws NotFoundException {
        return new ResponseEntity<>(articoliService.findByFamAssort(famAssort), HttpStatus.OK);
    }

    @GetMapping(value = "/cerca/famAssort/{famAssort}")
    public ResponseEntity<List<String>> getBarcodeByFamAssort(@PathVariable String famAssort) throws BindingException {
        return ResponseEntity.ok().body(barcodeService.findBarcodeByFamAssort(famAssort));
    }

    @Operation(summary = "Ricerca per Barcode", description = "restituisce un JSON")
    @GetMapping(value = "cerca/ean/{barcode}", produces = "application/json" )
    public ResponseEntity<Articoli> ListArtByEan(@PathVariable("barcode") String barcode) throws NotFoundException {
        return new ResponseEntity<>(barcodeService.findArticoloByBarcode(barcode), HttpStatus.OK);
    }

    @Operation(summary = "Ricerca per codice articolo", description = "restituisce un JSON")
    @GetMapping(value = "/cerca/codice/{codart}", produces = "application/json")
    public ResponseEntity<Articoli> ListArtByCod(@PathVariable("codart") String codart) throws NotFoundException{
        return new ResponseEntity<>(articoliService.findArticoloByCodArt(codart), HttpStatus.OK);
    }

    @Operation(summary = "Ricerca per descrizione", description = "restituisce un JSON")
    @GetMapping(value = "cerca/descrizione/{descrizione}", produces = "application/json")
    public ResponseEntity<List<Articoli>>  ListArtByDesc(@PathVariable("descrizione") String descrizione) throws NotFoundException{
        return new ResponseEntity<>(articoliService.findArticoliByDescrizione(descrizione), HttpStatus.OK);
    }

    @Operation(
            summary = "Ricerca per descrizione con paginazione",
            description = "restituisce un JSON"
    )
    @GetMapping(value = "cerca/descrizione/{descrizione}/{page}/{rows}", produces = "application/json")
    public ResponseEntity<List<Articoli>>  ListArtByDesc(@PathVariable("descrizione") String descrizione, @PathVariable("page") int pageNum,@PathVariable("rows") int RecXPage)  throws BindingException, NotFoundException{
        return new ResponseEntity<>(
                articoliService.findArticoliByDescrizione(descrizione, pageNum, RecXPage),
                HttpStatus.OK);
    }

    @Operation(summary = "Inserisce un nuovo elemento", description = "restituisce un JSON")
    @PostMapping(value = "/insert")
    public ResponseEntity<Articoli> createArt(@RequestBody Articoli articolo)
            throws BindingException, DuplicateException{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        articoliService.createArticolo(articolo);
        return new ResponseEntity<Articoli>(headers, HttpStatus.CREATED);

    }

    @PutMapping(value = "/update")
    public ResponseEntity<Articoli> UpdArt(@RequestBody Articoli articolo) throws NotFoundException,BindingException{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Articoli> (articoliService.updateArticolo(articolo),HttpStatus.CREATED);


    }

    @DeleteMapping(value = "/delete/{codart}")
    public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt)
            throws  NotFoundException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String message = articoliService.deleteArticolo(CodArt);
        return new ResponseEntity<>(Map.of("code", HttpStatus.OK.value(), "message", message),
                headers, HttpStatus.OK);
    }

}
