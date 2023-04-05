package com.siemens.mo.thirtyrails.core;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NoSuchElementException.class, EntityNotFoundException.class })
    protected ResponseEntity<String> handleNotFound(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<String> illegalArgumentOrState(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    protected ResponseEntity<String> handleAnyOtherError(RuntimeException ex) {
        return ResponseEntity
                .internalServerError()
                .body(ex.getMessage());
    }
}
