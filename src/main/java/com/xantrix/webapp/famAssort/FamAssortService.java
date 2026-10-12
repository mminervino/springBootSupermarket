package com.xantrix.webapp.famAssort;


import com.xantrix.webapp.dto.Dto;
import com.xantrix.webapp.exception.NotFoundException;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Map;

public interface FamAssortService {
    FamAssort findFamAssortById(int famAssort);
    List<Dto> countArticoliByFamAssort() throws NotFoundException;
}
