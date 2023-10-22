package ru.chernykh.MySecondTestAppSpringBoot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.chernykh.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(UnsupportedCodeException.class)
    public ResponseEntity<String> handleUnsupportedCodeException(UnsupportedCodeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}