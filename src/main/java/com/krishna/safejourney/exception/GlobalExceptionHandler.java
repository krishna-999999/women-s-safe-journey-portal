package com.krishna.safejourney.exception;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.krishna.safejourney.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return build(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 3. Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBad(BadRequestException ex) {
        return build(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 4. Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        return build(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 5. Conflict
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return build(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 6. DB Errors
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDB(DataIntegrityViolationException ex) {
        return build("Database constraint violation", HttpStatus.CONFLICT);
    }

    // 7. Content-Type error (your earlier bug)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMedia() {
        return build("Content-Type must be application/json",
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 8. Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        return build(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> build(String message, HttpStatus status) {
        return new ResponseEntity<>(
                new ErrorResponse(message, status.value(), LocalDateTime.now()),
                status
        );
    }
}


//Bad input → 400
//Unauthorized → 401
//Not found → 404
//Duplicate → 409

//Is request invalid? → BadRequest (400)
//
//Is user not allowed? → Unauthorized (401)
//
//Is data not found? → NotFound (404)
//
//Is data already exists? → Conflict (409)
//
//Everything else → 500 (unexpected)