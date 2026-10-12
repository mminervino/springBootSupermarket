package com.xantrix.webapp.entities;

import java.io.Serializable;

import com.xantrix.webapp.articoli.Articoli;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "INGREDIENTI")
public class Ingredienti implements Serializable
{
    private static final long serialVersionUID = -6230810713799336046L;

    @Id
    @Column(name = "CODART",length = 20)
    private String codArt;

    @Column(name = "INFO",length = 300)
    @Size(min = 1, max = 300)
    private String info;

    @OneToOne
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Articoli articolo;


    @Version
    private long version;

}