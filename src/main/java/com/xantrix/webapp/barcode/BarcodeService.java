package com.xantrix.webapp.barcode;

import com.xantrix.webapp.articoli.Articoli;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.NotFoundException;

import java.util.List;

public interface BarcodeService
{
    public Barcode SelByBarcode(String Barcode);
    Articoli findArticoloByBarcode(String barcode) throws NotFoundException;
    List<String> findBarcodeByFamAssort(String famAssort) throws BindingException;

}
