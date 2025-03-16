package com.example.jwt.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Product already in cart")
public class ExistingProductInCartException extends RuntimeException {
    public ExistingProductInCartException(String message) {
        super(message);
    }
}
