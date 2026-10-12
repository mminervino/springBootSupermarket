package com.xantrix.webapp.articoli;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticoliRepository extends JpaRepository<Articoli, String>
{

    List<Articoli> findByDescrizioneLike(String descrizione);

    List<Articoli> findByDescrizioneLike(String descrizione, Pageable pageable);

    Articoli findByCodArt(String codArt);

    List<Articoli> findAll();

    @Query("select a from Articoli a where a.famAssort.id = :famAssortId")
    List<Articoli> findByFamAssortId(@Param("famAssortId") int famAssortId);

}
