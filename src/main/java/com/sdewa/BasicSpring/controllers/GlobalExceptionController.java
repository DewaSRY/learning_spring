package com.sdewa.BasicSpring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sdewa.BasicSpring.exception.CommonContentNotFound;
import com.sdewa.BasicSpring.models.CommonError;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(CommonContentNotFound.class)
    public ResponseEntity<CommonError> handleCommonContentNotFound(CommonContentNotFound ex, WebRequest request) {
        
        CommonError error = CommonError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
                
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
}
