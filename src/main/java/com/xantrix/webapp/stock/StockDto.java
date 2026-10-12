package com.xantrix.webapp.stock;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto implements Serializable {
    private long idStock;
    private String codart;
    private int pezzi;
    private double peso;
    private double valorestock;
    private String codStat;
}
