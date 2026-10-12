package com.xantrix.webapp.articoli;

import java.util.List;

import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

public interface ArticoliService
{
    public Iterable<Articoli> SelTutti();

    public List<Articoli> SelByDescrizione(String descrizione);

    public List<Articoli> SelByDescrizione(String descrizione, Pageable pageable);

    public Articoli SelByCodArt(String codArt);

    public void DelArticolo(Articoli articolo);

    public Articoli InsArticolo(Articoli articolo);

    List<Articoli> findAll();

    List<Articoli> findByFamAssort(int id) throws NotFoundException;

    Articoli findArticoloByCodArt(String codArt) throws NotFoundException;

    List<Articoli> findArticoliByDescrizione(String descrizione) throws NotFoundException;

    List<Articoli> findArticoliByDescrizione(String descrizione, Pageable pageable) throws NotFoundException;

    List<Articoli> findArticoliByDescrizione(String descrizione, int page, int rows)
            throws BindingException, NotFoundException;

    void createArticolo(Articoli articolo) throws BindingException, DuplicateException;

    Articoli updateArticolo(Articoli articolo) throws BindingException, NotFoundException , ObjectOptimisticLockingFailureException;

    String deleteArticolo(String codArt) throws NotFoundException , ObjectOptimisticLockingFailureException;
}