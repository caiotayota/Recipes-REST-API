package com.caiotayota.recipes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistsException extends RuntimeException {
    
    public UsernameExistsException() {
        super("BAD REQUEST: A user with this username already exists");
    }
}
