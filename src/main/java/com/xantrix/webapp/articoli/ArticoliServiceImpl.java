package com.xantrix.webapp.articoli;

import com.xantrix.webapp.famAssort.FamAssort;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.famAssort.FamAssortService;
import com.xantrix.webapp.stock.StockRepository;
import com.xantrix.webapp.stock.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticoliServiceImpl implements ArticoliService {
    private final ArticoliRepository articoliRepository;
    private final FamAssortService famAssortService;
    private final Validator validator;
    private final StockService stockService;
    Logger logger = LoggerFactory.getLogger(ArticoliServiceImpl.class);

    @Override
    public List<Articoli> findByFamAssort(int famAssort) throws NotFoundException {
        FamAssort famiglia = famAssortService.findFamAssortById(famAssort);
        if (famiglia == null) {
            throw new NotFoundException("FamAssort id non presente");
        }

        List<Articoli> articoli = articoliRepository.findByFamAssortId(famiglia.getId());
        if (articoli.isEmpty()) {
            throw new NotFoundException("Articoli di quel tipo non presenti");
        }
        return articoli;
    }

    @Override
    public Articoli findArticoloByCodArt(String codArt) throws NotFoundException {
        Articoli articolo = SelByCodArt(codArt);
        if (articolo == null) {
            throw new NotFoundException("L'articolo con codice " + codArt + " non è stato trovato!");
        }
        return articolo;
    }

    @Override
    public List<Articoli> findArticoliByDescrizione(String descrizione) throws NotFoundException {
        List<Articoli> articoli = SelByDescrizione(descrizione + "%");
        return requireResults(articoli, descrizione);
    }

    @Override
    public List<Articoli> findArticoliByDescrizione(String descrizione, Pageable pageable) throws NotFoundException {
        List<Articoli> articoli = SelByDescrizione(descrizione + "%", pageable);
        return requireResults(articoli, descrizione);
    }

    private List<Articoli> requireResults(List<Articoli> articoli, String descrizione) throws NotFoundException {
        if (articoli.isEmpty()) {
            throw new NotFoundException("non sono stati trovati articoli con descrizione: " + descrizione);
        }
        return articoli;
    }

    @Override
    public List<Articoli> findArticoliByDescrizione(String descrizione, int page, int rows)
            throws BindingException, NotFoundException {
        if (page < 0 || rows == 0) {
            throw new BindingException("Pagina o numero di righe non valido");
        }
        int pageSize = rows < 0 ? 10 : rows;
        return findArticoliByDescrizione(descrizione, PageRequest.of(page, pageSize));
    }

    @Override
    public void createArticolo(Articoli articolo) throws BindingException, DuplicateException {

            validate(articolo);
            if (SelByCodArt(articolo.getCodArt()) != null) {
                throw new DuplicateException("Articolo " + articolo.getCodArt()
                        + " presente in anagrafica! Impossibile utilizzare il metodo POST");
            }
            InsArticolo(articolo);


    }

    @Transactional
    @Retryable(retryFor = DataAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 20))
    @Override
    public Articoli updateArticolo(Articoli articolo) throws BindingException, NotFoundException ,ObjectOptimisticLockingFailureException {
        validate(articolo);
        Articoli res = articoliRepository.findById(articolo.getCodArt())
                .orElseThrow(() -> new NotFoundException("L'articolo " + articolo.getCodArt()
                        + " non è presente in anagrafica! Impossibile utilizzare il metodo PUT"));

        res.setDescrizione(articolo.getDescrizione());
        res.setUm(articolo.getUm());
        res.setPesoNetto(articolo.getPesoNetto());
        res.setDataCreaz(articolo.getDataCreaz());
        res.setFamAssort(articolo.getFamAssort());
        res.setIva(articolo.getIva());
        res.setPrezzo(articolo.getPrezzo());
        if (articolo.getIngredienti() != null) {
            res.setIngredienti(articolo.getIngredienti());
        }

        stockService.aggiornaValoreStock(res);
        return res;
    }

    @Override
    public String deleteArticolo(String codArt) throws NotFoundException , ObjectOptimisticLockingFailureException {
        Articoli articolo = SelByCodArt(codArt);
        if (articolo == null) {
            throw new NotFoundException(String.format("Articolo %s non presente in anagrafica! ", codArt));
        }
        DelArticolo(articolo);
        return "Eliminazione Articolo " + codArt + " Eseguita Con Successo";
    }

    private void validate(Articoli articolo) throws BindingException {
        Set<ConstraintViolation<Articoli>> violations = validator.validate(articolo);
        if (!violations.isEmpty()) {
            throw new BindingException(violations.iterator().next().getMessage());
        }
    }

    @Override
    public Iterable<Articoli> SelTutti(){
        return articoliRepository.findAll();
    }
    @Override
    public List<Articoli> SelByDescrizione(String descrizione, Pageable pageable){
        return articoliRepository.findByDescrizioneLike(descrizione, pageable);
    }
    @Override
    public List<Articoli> SelByDescrizione(String descrizione){
        return articoliRepository.findByDescrizioneLike(descrizione);
    }

    @Override
    public List<Articoli> findAll(){return articoliRepository.findAll();}
    @Override
    public Articoli SelByCodArt(String codArt) {
        return articoliRepository.findByCodArt(codArt);
    }
    @Override
    public void DelArticolo(Articoli articolo){
        articoliRepository.delete(articolo);
    }

    @Override
    public Articoli InsArticolo(Articoli Articolo){
        return articoliRepository.save(Articolo);
    }
}
