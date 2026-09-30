package com.xantrix.webapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "barcode")
public class Barcode implements Serializable {

    @Id
    @Column(name = "BARCODE")
    @NotNull (message = "...")
    @Size(min = 8,max = 13, message = "...")
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "CODART", referencedColumnName = "codArt")
    @JsonBackReference
    private Articoli articolo;

    @Column(name = "IDTIPOART")
    @NotNull(message = "...")
    private String idTipoArt;


    public String getBarcode() {return barcode;}
    public void setBarcode(String barcode) {this.barcode = barcode;}
    public Articoli getArticolo() {return articolo;}
    public String getIdTipoArt() {return idTipoArt;}
    public void setArticolo(Articoli articolo) {this.articolo = articolo;}
    public void setIdTipoArt(String idTipoArt) {this.idTipoArt = idTipoArt;}

}
