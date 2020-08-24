package com.company.managementservice.model.enums;


public enum IncrementType {
    ABSOLUTE,
    PERCENTAGE;

    /*private String key;

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

    }*/
}
