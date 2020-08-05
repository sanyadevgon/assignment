package com.company.managementservice.exception;

import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ServiceResponse<?> runTimeException(RuntimeException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND, false),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DublicateDataException.class)
    public ServiceResponse<?> dublicateDataException(DublicateDataException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmptyBodyException.class)
    public ServiceResponse<?> EmptyBodyException(EmptyBodyException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ServiceResponse<?> DataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ServiceResponse<?> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }



}

