package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.enums.CurrencyType;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConvertorService {


    public long getRupeeValue(Long value, String curr) throws NotFoundException {
        try {
            CurrencyType c = CurrencyType.valueOf(curr.toUpperCase());
            return c.Rupeevalue * value;
        }catch (IllegalArgumentException e ){
            throw new NotFoundException("Currency type not found ");
        }
    }
}