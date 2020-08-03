package com.company.managementservice.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessageResponse<T>{

    private T data;
    private HttpStatus code;
    private boolean status;



}
