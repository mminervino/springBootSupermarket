package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "ARTICOLI")
@Data
public class Articoli  implements Serializable
{
    private static final long serialVersionUID = 7361753083273455478L;

    @Id
    @Column(name = "CODART")
    @Size(min = 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
    @NotNull(message = "{NotNull.Articoli.codArt.Validation}")
    private String codArt;

    @Column(name = "DESCRIZIONE")
    @Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
    private String descrizione;

    @Column(name = "UM")
    private String um;

    @Column(name = "CODSTAT")
    private String codStat;

    @Column(name = "PZCART")
    @Max(value = 99, message = "{Max.Articoli.pzCart.Validation}")
    private Integer pzCart;

    @Column(name = "PESONETTO")
    @Min(value = (long) 0.01, message = "{Min.Articoli.pesoNetto.Validation}")
    private double pesoNetto;

    @Column(name = "IDSTATOART")
    @NotNull(message= "{NotNull.Articoli.idStatoArt.Validation}")
    private String idStatoArt;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATACREAZIONE")
    private Date dataCreaz;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
    @JsonManagedReference
    private Set<Barcode> barcode = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "IDFAMASS", referencedColumnName = "ID")
    @NotNull(message = "{NotNull.Articoli.famAssort.Validation}")
    private FamAssort famAssort;

    @OneToOne(mappedBy = "articolo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ingredienti ingredienti;

    @ManyToOne
    @JoinColumn(name = "IDIVA", referencedColumnName = "idIva")
    private Iva iva;



}
