package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "famassort")
@Data
public class FamAssort implements Serializable
{
    private static final long serialVersionUID = 3788120361600509595L;

    @Id
    //@NotNull(message = "{NotNull.FamAssort.id.Validation}")
    @Column(name = "ID")
    private int id;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "famAssort")
    //@JsonBackReference
    @JsonIgnore
    private Set<Articoli> articoli = new HashSet<>();

}
