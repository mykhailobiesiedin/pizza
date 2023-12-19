package com.example.pizza.exception.exceptions;

public class CafeNotFoundException extends RuntimeException {

    public CafeNotFoundException(String message) {
        super(message);
    }
}
