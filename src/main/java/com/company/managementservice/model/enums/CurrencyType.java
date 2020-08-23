package com.company.managementservice.model.enums;

public enum CurrencyType {


    DOLLAR(80L),
    EURO(100L),
    RUPEES(1L);

    public final Long rupeeValue;

    CurrencyType(Long value){
        this.rupeeValue=value;

    }


}
