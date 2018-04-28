package com.sellics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AmazonApiException extends RuntimeException{
    public AmazonApiException(String message) {
        super(message);
    }
}
