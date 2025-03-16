package com.example.jwt.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Product is out of stock")
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(short availableStock) {
        super("Insufficient stock: Only " + availableStock + " items left.");
    }
}