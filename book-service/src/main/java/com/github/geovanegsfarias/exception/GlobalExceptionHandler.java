package com.github.geovanegsfarias.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorList = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .sorted()
                .toList();

        var problemDetail = ProblemDetail.forStatusAndDetail(status, "Validation error");
        problemDetail.setProperty("errors", errorList);

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleBookNotFoundException(BookNotFoundException e, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<ProblemDetail> handleBookUnavailableException(BookUnavailableException e, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedAccessException(UnauthorizedAccessException e, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGlobalException(Exception e, WebRequest request) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected internal error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}