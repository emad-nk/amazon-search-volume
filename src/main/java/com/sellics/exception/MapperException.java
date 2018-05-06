package com.sellics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MapperException extends Exception{
    public MapperException(String message) {
        super(message);
    }
}
