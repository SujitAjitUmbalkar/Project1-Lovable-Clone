package com.codingshuttle.projects.lovable_clone.error;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex)
    {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(apiError.toString() , ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getResourceName()+" with id "+ex.getResourceId()+" Not Found");
        log.error(apiError.toString() , ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        List<ApiFieldError> errors =  ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiFieldError(error.getField() , error.getDefaultMessage()))
                .toList();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST , "Input Validation Failed", errors);
        log.error(apiError.toString() , ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Username not found with username: " + ex.getMessage());
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        // Removed 'message:'
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Authentication failed: " + ex.getMessage());
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid JWT token: " + ex.getMessage());
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, "Access denied: Insufficient permissions");
        log.error(apiError.toString(), ex);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}