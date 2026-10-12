package com.xantrix.webapp.famAssort;

import com.xantrix.webapp.dto.Dto;
import com.xantrix.webapp.exception.NotFoundException;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FamAssortServiceImpl implements FamAssortService  {
    final FamAssortRepository famAssortRepository;
    public FamAssort findFamAssortById(int famAssort){
        return famAssortRepository.findFamAssortById(famAssort);
    }

    public List<Dto> countArticoliByFamAssort() throws NotFoundException{
        List< Dto > res = famAssortRepository.countArticoliByFamAssort();
        if(res.isEmpty()){
            throw new NotFoundException("non ci sono famiglie di articoli dichiarate");
        }

        return res;
    }
}
