package com.xantrix.webapp.barcode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xantrix.webapp.articoli.Articoli;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "barcode")

@EntityListeners(AuditingEntityListener.class)
public class Barcode implements Serializable {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime  lastModifiedDate;
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

    @Version
    private long version;


    public String getBarcode() {return barcode;}
    public void setBarcode(String barcode) {this.barcode = barcode;}
    public Articoli getArticolo() {return articolo;}
    public String getIdTipoArt() {return idTipoArt;}
    public void setArticolo(Articoli articolo) {this.articolo = articolo;}
    public void setIdTipoArt(String idTipoArt) {this.idTipoArt = idTipoArt;}

}
