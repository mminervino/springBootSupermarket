package com.xantrix.webapp.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.xantrix.webapp.articoli.Articoli;
import jakarta.persistence.*;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.OverridesAttribute;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "IVA")
@EntityListeners(AuditingEntityListener.class)
public class Iva
{
    @Id
    @Column(name = "IDIVA")
    private int idIva;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @Column(name = "ALIQUOTA")
    private int aliquota;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "iva")
    //@JsonBackReference
    @JsonIgnore
    private Set<Articoli> articoli = new HashSet<>();

    @Version
    private int version;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedDate
    private LocalDateTime createdDate;



}
