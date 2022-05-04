package com.musala.drone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WeightException extends ResponseStatusException {

    public WeightException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }

}
