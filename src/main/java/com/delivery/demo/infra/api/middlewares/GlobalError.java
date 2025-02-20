package com.delivery.demo.infra.api.middlewares;

import com.delivery.demo.infra.config.errors.NotFoundException;
import com.delivery.demo.infra.config.errors.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.delivery.demo.infra.config.errors.ErrorResponse;

@ControllerAdvice
public class GlobalError {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred.", 500);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getType(), ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getType(), ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
