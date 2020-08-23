package com.company.managementservice.model.enums;

import com.company.managementservice.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.oracle.javafx.jmx.json.JSONException;

import java.io.IOException;

public enum IncrementType {
    ABSOLUTE("absolute"),
    PERCENTAGE("percentage");

    private String key;

    IncrementType(String key) {
        this.key = key;
    }

    @JsonCreator
    public static IncrementType fromString(String key) throws NotFoundException {

        try {
            IncrementType incrementType=IncrementType.valueOf(key.toUpperCase());
            return incrementType;
        }catch (IllegalArgumentException e ){
            throw new NotFoundException("Increment type not found ");
        }

    }
}
