package com.xantrix.webapp.service;

import com.xantrix.webapp.entities.Barcode;
import com.xantrix.webapp.repository.BarcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarcodeServiceImp implements BarcodeService{
    @Autowired
    BarcodeRepository barcodeRepository;
    @Override
    public Barcode SelByBarcode(String Barcode){

        return barcodeRepository.findByBarcode(Barcode);

    }

}
