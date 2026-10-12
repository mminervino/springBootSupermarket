package com.xantrix.webapp.articoli;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xantrix.webapp.barcode.Barcode;
import com.xantrix.webapp.entities.BaseEntity;
import com.xantrix.webapp.famAssort.FamAssort;
import com.xantrix.webapp.entities.Ingredienti;
import com.xantrix.webapp.entities.Iva;
import com.xantrix.webapp.stock.Stock;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ARTICOLI")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Articoli implements Serializable {
    private static final long serialVersionUID = 7361753083273455478L;

    @Id
    @Column(name = "CODART",length = 20)
    @Size(min = 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
    @NotNull(message = "{NotNull.Articoli.codArt.Validation}")
    private String codArt;

    @Column(name = "DESCRIZIONE")
    @Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
    private String descrizione;

    @Column(name = "UM")
    private String um;

    @Column(name = "PESONETTO")
    @Min(value = (long) 0.01, message = "{Min.Articoli.pesoNetto.Validation}")
    private double pesoNetto;


    @Temporal(TemporalType.DATE)
    @Column(name = "DATACREAZIONE")
    private Date dataCreaz;

    @ManyToOne
    @JoinColumn(name = "IDFAMASS", referencedColumnName = "ID")
    @NotNull(message = "{NotNull.Articoli.famAssort.Validation}")
    private FamAssort famAssort;

    @OneToOne(mappedBy = "articolo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ingredienti ingredienti;

    @ManyToOne
    @JoinColumn(name = "IDIVA", referencedColumnName = "idIva")
    private Iva iva;

    @Column(name = "PREZZO")
    private float prezzo = 0.1f;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
    @JsonIgnore
    private Set<Stock> stock = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
    @JsonIgnore
    private Set<Barcode> barcode = new HashSet<>();

    @Version
    @Column(name = "VERSIONE")
    private long versione;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime  lastModifiedDate;

}
