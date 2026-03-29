package com.reseller.panel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reseller.panel.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(ResourceNotFoundException ex) {

        logger.error("Resource not found: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequest(BadRequestException ex) {

        logger.warn("Bad request: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicate(DuplicateResourceException ex) {

        logger.warn("Duplicate resource: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.failure(ex.getMessage()));
    }

 
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorized(UnauthorizedException ex) {

        logger.warn("Unauthorized access: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.failure(ex.getMessage()));
    }

 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {

        logger.error("Internal server error: ", ex); 

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("Something went wrong"));
    }
}