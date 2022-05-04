package com.musala.drone.controller;

import com.musala.drone.responses.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(
            HttpMessageNotReadableException exception,
            WebRequest request
    ){
        log.error("Unable to construct message from body.", exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        var response = buildErrorResponse(ex,"Field validation failed", HttpStatus.BAD_REQUEST,request);

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            Objects.requireNonNull(response.getBody()).addValidationError(fieldName,errorMessage);
        });
        log.info("Error here: {}",response);
        return response;
    }


        private ResponseEntity<ErrorResponse> buildErrorResponse(
                Exception exception,
                String message,
                HttpStatus httpStatus,
                WebRequest request
        ) {
            ErrorResponse errorResponse = new ErrorResponse(
                    httpStatus.value(),
                    message
            );

            return ResponseEntity.status(httpStatus).body(errorResponse);
        }


}
