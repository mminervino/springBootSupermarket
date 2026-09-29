package com.xantrix.webapp.service;

import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.repository.ArticoliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticoliServiceImpl implements ArticoliService {
    @Autowired
    ArticoliRepository articoliRepository;

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
    public Articoli SelByCodArt(String codArt) {
        return articoliRepository.findByCodArt(codArt);
    }
    @Override
    public void DelArticolo(Articoli articolo){
        articoliRepository.delete(articolo);
    }

    @Override
    public void InsArticolo(Articoli Articolo){
        articoliRepository.save(Articolo);
    }
}
