package com.linkometrics.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   //This class contains handlers for exceptions thrown by my controllers.
public class GlobalExceptionHandler {

    //If a UrlNotFoundException occurs, use the method immediately below to handle it.
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<String> handleUrlNotFound(UrlNotFoundException myException)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(myException.getMessage());
    }
}
