package com.company.managementservice.model.response;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ServiceResponse<T> extends ResponseEntity {

    public ServiceResponse(T data) {
        super(data, HttpStatus.OK);
    }

    public ServiceResponse(T data, HttpStatus status) {
        super(data, status);
    }


}
