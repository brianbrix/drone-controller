package com.musala.drone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NullObjectException extends ResponseStatusException {

    public NullObjectException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }

}