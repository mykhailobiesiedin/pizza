package com.example.pizza.exception.exceptions;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(String message) {
        super(message);
    }
}
