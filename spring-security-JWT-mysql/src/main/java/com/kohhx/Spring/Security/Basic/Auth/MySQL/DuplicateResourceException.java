package com.kohhx.Spring.Security.Basic.Auth.MySQL;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class DuplicateResourceException extends Exception{
    public DuplicateResourceException(String message) {
        super(message);
    }
}
