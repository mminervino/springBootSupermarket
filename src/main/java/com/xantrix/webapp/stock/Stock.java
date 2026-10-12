package com.xantrix.webapp.stock;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xantrix.webapp.articoli.Articoli;
import com.xantrix.webapp.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Table(name = "stock")
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "IDSTOCK"))
public class Stock extends BaseEntity {


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CODART", referencedColumnName = "CODART")
    private Articoli articolo;

    @JsonProperty("codArt")
    public String getCodArt() {
        return articolo == null ? null : articolo.getCodArt();
    }

    @JsonProperty("codArt")
    public void setCodArt(String codArt) {
        if (codArt == null) {
            articolo = null;
            return;
        }

        Articoli riferimento = new Articoli();
        riferimento.setCodArt(codArt);
        articolo = riferimento;
    }

    @Column(name = "PEZZI")
    private int pezzi;

    @Column(name = "PESO")
    private float peso;

    @Column(name = "VALORESTOCK", nullable = false)
    @Setter(AccessLevel.NONE)
    private float valoreStock;

    @Column(name = "CODSTAT")
    @Size(min = 1, max = 45)
    private String codStat;


    public Stock(StockDto stockDto) {
        this.setId(stockDto.getIdStock());

        this.pezzi = stockDto.getPezzi();
        this.peso = (float) stockDto.getPeso();
        this.valoreStock = (float) stockDto.getValorestock();
        this.codStat = stockDto.getCodStat();
    }

    public void setValoreStock(Articoli articolo) {
        this.valoreStock = switch(articolo.getUm()){
            case "KG" -> articolo.getPrezzo() * this.peso;
            case "PZ" -> articolo.getPrezzo() * this.pezzi;
            default -> throw new IllegalArgumentException();
        };
    }
}
