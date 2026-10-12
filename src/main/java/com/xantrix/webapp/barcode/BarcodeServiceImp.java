package com.xantrix.webapp.barcode;

import com.xantrix.webapp.articoli.Articoli;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.articoli.ArticoliRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarcodeServiceImp implements BarcodeService{
    private final ArticoliRepository articoliRepository;
    private final BarcodeRepository barcodeRepository;
    @Override
    public Barcode SelByBarcode(String Barcode){

        return barcodeRepository.findByBarcode(Barcode);

    }

    @Override
    public Articoli findArticoloByBarcode(String barcode) throws NotFoundException {
        Barcode ean = SelByBarcode(barcode);
        if (ean == null) {
            throw new NotFoundException("Il barcode " + barcode + " non è stato trovato!");
        }
        return ean.getArticolo();
    }

    @Override
    public List<String> findBarcodeByFamAssort(String famAssordID) throws BindingException {
        if (famAssordID == null || famAssordID.isBlank()) {
            throw new BindingException("inserire un campo valido");
        }
        return barcodeRepository.findBarcodeByFamAssort(famAssordID);
    }


}
