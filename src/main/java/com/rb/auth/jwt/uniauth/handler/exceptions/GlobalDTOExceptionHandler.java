package com.rb.auth.jwt.uniauth.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalDTOExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<? extends List<String>> handleBeanValidationException(MethodArgumentNotValidException exception){
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errors.add(fieldError.getDefaultMessage()));
        return new ResponseEntity<List<String>>(errors, HttpStatus.FORBIDDEN);
    }
}
