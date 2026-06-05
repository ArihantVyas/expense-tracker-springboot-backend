package com.arihant.expense_tracker.exception;

import com.arihant.expense_tracker.error_response.GeneralErrorResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralErrorResponseStructure> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        GeneralErrorResponseStructure errorResponse = new GeneralErrorResponseStructure();

        // errorResponse.message = exception.getMessage();

        errorResponse.message =
                exception
                        .getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();
        errorResponse.dateTime = LocalDateTime.now();
        errorResponse.status_code = HttpStatus.BAD_REQUEST.value();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GeneralErrorResponseStructure> handleResourceNotFoundException(ResourceNotFoundException exception){
        GeneralErrorResponseStructure errorResponse = new GeneralErrorResponseStructure();

        errorResponse.message = exception.getMessage();
        errorResponse.dateTime = LocalDateTime.now();
        errorResponse.status_code = HttpStatus.NOT_FOUND.value();

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }
}
