package com.xantrix.webapp.service;

import com.xantrix.webapp.entities.Barcode;
import com.xantrix.webapp.repository.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarcodeServiceImp implements BarcodeService{
    private final BarcodeRepository barcodeRepository;
    @Override
    public Barcode SelByBarcode(String Barcode){

        return barcodeRepository.findByBarcode(Barcode);

    }

}
