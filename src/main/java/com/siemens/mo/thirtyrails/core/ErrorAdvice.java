package com.siemens.mo.thirtyrails.core;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NoSuchElementException.class, EntityNotFoundException.class })
    protected ResponseEntity<String> handleNotFound(Exception e) {
        log.error("Something not found", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<String> illegalArgumentOrState(Exception e) {
        log.error("Wrong time or wrong thing", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    protected ResponseEntity<String> handleAnyOtherError(RuntimeException ex) {
        log.error("Something went wrong", ex);
        return ResponseEntity
                .internalServerError()
                .body(ex.getMessage());
    }
}
