package com.musala.drone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BatteryException extends ResponseStatusException {

    public BatteryException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }

}
