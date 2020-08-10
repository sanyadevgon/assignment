package com.company.managementservice.exception;

import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ServiceResponse<?> runTimeException(NotFoundException ex) {
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
    public ServiceResponse<?> emptyBodyException(EmptyBodyException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ServiceResponse<?> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ServiceResponse<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage().toString(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = javax.validation.ConstraintViolationException.class)
    public ServiceResponse<?> constraintViolationException(javax.validation.ConstraintViolationException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage().toString(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    public ServiceResponse<?> invalidFormatException(InvalidFormatException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage().toString(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = org.springframework.security.web.firewall.RequestRejectedException.class)
    public ServiceResponse<?> requestRejectedException(
            org.springframework.security.web.firewall.RequestRejectedException ex) {
        log.info("Unable to complete request. Exception occurred: {}", ex.getMessage());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse<>(ex.getMessage().toString(), HttpStatus.BAD_REQUEST, false),
                HttpStatus.BAD_REQUEST);
    }

}

