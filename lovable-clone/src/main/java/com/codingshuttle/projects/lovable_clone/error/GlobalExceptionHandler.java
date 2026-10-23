package com.codingshuttle.projects.lovable_clone.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
