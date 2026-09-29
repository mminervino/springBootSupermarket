package com.xantrix.webapp.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "INGREDIENTI")
@Data
public class Ingredienti implements Serializable
{
    private static final long serialVersionUID = -6230810713799336046L;

    @Id
    @Column(name = "CODART")
    private String codArt;

    @Column(name = "INFO")
    private String info;

    @OneToOne
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Articoli articolo;

}