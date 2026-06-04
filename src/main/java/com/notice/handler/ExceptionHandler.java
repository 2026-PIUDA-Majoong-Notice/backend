package com.notice.handler;

import com.notice.exception.ErrorResponse;
import com.notice.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        ErrorResponse response = ErrorResponse.of(
                "VALIDATION_ERROR",
                message
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponse> handleHttpException(
            HttpException exception
    ) {
        ErrorResponse response = ErrorResponse.of(
                exception.getCode(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }
}