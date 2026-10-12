package com.xantrix.webapp.famAssort;

import com.xantrix.webapp.dto.Dto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamAssortRepository extends JpaRepository<FamAssort,Integer>{

    @Query("select f from FamAssort f where f.id = :ID")
    FamAssort findFamAssortById(@Param("ID") int ID);

    @Query("select  new com.xantrix.webapp.dto.Dto (Count(a) , f.id , f.descrizione ) from Articoli a join FamAssort f on a.famAssort.id = f.id group by f.id, f.descrizione order by f.descrizione")
    List<Dto> countArticoliByFamAssort();
}
