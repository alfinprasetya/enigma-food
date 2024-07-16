package com.enigma.food.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.food.utils.dto.WebResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<WebResponse<?>> constraintViolationException(ConstraintViolationException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(WebResponse.<String>builder().status(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(exception.getMessage()).build());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<WebResponse<?>> responseStatusException(ResponseStatusException exception) {
    return ResponseEntity.status(exception.getStatusCode())
      .body(WebResponse.<String>builder().status(exception.getStatusCode().toString()).message(exception.getReason()).build());
  }
}

